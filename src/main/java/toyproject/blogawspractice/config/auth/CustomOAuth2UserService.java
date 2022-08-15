package toyproject.blogawspractice.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import toyproject.blogawspractice.config.auth.dto.OAuthAttributes;
import toyproject.blogawspractice.config.auth.dto.SessionUser;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.repository.user.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. userRequest -> OAuth2User 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 2. 로그인 정보들을 서비스에서 사용하기 위해 변환
        // 현재 로그인 진행 중인 서비스를 구분하는 코드 -> 구글, 네이버, 카카오를 서로 구분함
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드값임. (primary key와 같은 의미) -> 구글, 네이버, 카카오 로그인 동시 지원할 때 사용
        // google 기본 코드 = "sub" (네이버, 카카오는 지원하지 않는다.)
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스 (구글, 네이버, 카카오 사용)
        // OAuth2User -> OAuthAttributes(static 메서드를 이용한 객체 생성)
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);  // 소셜 로그인한 사용자 정보가 업데이트 되었을 때를 대비한 update기능도 있음.

        // SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
