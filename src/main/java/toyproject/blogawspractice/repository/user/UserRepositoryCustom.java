package toyproject.blogawspractice.repository.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import toyproject.blogawspractice.domain.user.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> getUserFromEmail(String email);
    Optional<User> getUserFromOAuth2User(OAuth2User oAuth2User);
}
