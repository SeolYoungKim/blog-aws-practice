package toyproject.blogawspractice.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import toyproject.blogawspractice.config.auth.dto.SessionUser;
import toyproject.blogawspractice.repository.user.UserRepository;

import java.util.Map;

public class WithMockCustomUserSecurityFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

//        CustomUserDetails principal = new (customUser.username(), customUser.userEmail(), customUser.userPicture(), customUser.userRole());

        User user = User.builder()
                .username(customUser.username())
                .userEmail(customUser.email())
                .userPicture(customUser.userPicture())
                .userRole(Role.USER)
                .build();
        userRepository.save(user);

        SessionUser sessionUser = new SessionUser(user);

        //TODO: 내 코드에서 UserDetails를 가져올 수 있는 방법은?

        // ㅇㅒ는 유저네임/패스워드 인증용이라, 설정 가능 값이 적다.. 그리고 나는 OAuth2 객체를 사용하니, 그에 맞는 가짜 객체를 만들어줘야 할 것이다.
        Authentication auth1 = new UsernamePasswordAuthenticationToken(sessionUser, null, AuthorityUtils.createAuthorityList(Role.USER.name()));

        Map<String, Object> attr = Map.of(
                "username", customUser.username(),
                "email", customUser.email(),
                "userPicture", customUser.userPicture()
        );
        //TODO: OAuth Authentication 객체를 만들어내기만 하면 될것같은데.. 공식문서를 더 파헤쳐보자.

        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(AuthorityUtils.createAuthorityList(Role.USER.name()), attr, "username");

        Authentication auth = new OAuth2AuthenticationToken(defaultOAuth2User, AuthorityUtils.createAuthorityList(Role.USER.name()), "1");


        context.setAuthentication(auth);

        return context;
    }
}
