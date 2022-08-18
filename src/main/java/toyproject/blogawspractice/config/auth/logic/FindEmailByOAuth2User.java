package toyproject.blogawspractice.config.auth.logic;

import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

/**
 * @AuthenticationPrincipal 로 가져온 OAuth2User 객체에서 이메일 주소만 뽑아내는 로직
 */

@Getter
public class FindEmailByOAuth2User {

    public static String findEmail(OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();

        // 1차로 containsKey(email)로 거름 -> 구글, 네이버에서는 이메일 득
        if (attributes.containsKey("email")) {
            return (String) attributes.get("email");
        } else {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email");
        }
    }
}
