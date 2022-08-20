package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.exception.NullUserException;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.web.response.user.ResponseUser;

import static toyproject.blogawspractice.config.auth.logic.FindEmailByOAuth2User.findEmail;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseUser readUser(OAuth2User oAuth2User) {
        User user = userRepository.getUserFromEmail(findEmail(oAuth2User))
                .orElseThrow(NullUserException::new);

        return new ResponseUser(user);
    }

}
