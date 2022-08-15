package toyproject.blogawspractice.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String userName;
    private final String userEmail;
    private final String userPicture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String userName, String userEmail, String userPicture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPicture = userPicture;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map 객체이다.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        // TODO: userNameAttributeName? 이를 "id"로 설정한 이유를 알아보자.
        if ("naver".equals(registrationId)) { // 이는 yml 파일에 적어준 registration id임. google, naver, kakao 셋중 하나임
            return ofNaver("id", attributes);
        }

        if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .userName((String) attributes.get("name"))
                .userEmail((String) attributes.get("email"))
                .userPicture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .userName((String) response.get("name"))
                .userEmail((String) response.get("email"))
                .userPicture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");

        return OAuthAttributes.builder()
                .userName((String) profile.get("nickname"))
                .userEmail((String) response.get("email"))
                .userPicture((String) profile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(userName)
                .userEmail(userEmail)
                .userPicture(userPicture)
                .userRole(Role.GUEST)
                .build();
    }
}
