package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.exception.NullUserException;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.web.request.user.RequestEditUser;
import toyproject.blogawspractice.web.request.user.RequestUpdateUserInfo;
import toyproject.blogawspractice.web.response.user.ResponseUser;

import java.util.List;
import java.util.stream.Collectors;

import static toyproject.blogawspractice.config.auth.logic.FindEmailByOAuth2User.findEmail;
import static toyproject.blogawspractice.DefaultAdminInfo.DEFAULT_ADMIN_EMAIL;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseUser readUser(OAuth2User oAuth2User) {
        User user = getUserByEmail(findEmail(oAuth2User));

        return new ResponseUser(user);
    }

    @Transactional(readOnly = true)
    public List<ResponseUser> findAllUser() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getUserEmail().equals(DEFAULT_ADMIN_EMAIL))
                .map(ResponseUser::new)
                .collect(Collectors.toList());
    }

    public void editRole(List<RequestEditUser> editUsers) {

        // TODO: 해당 로직은 어쩌면 if문이 더 간결할지도 모르겠다. 고민을 해보자.
        editUsers.stream()
                .filter(editUser -> editUser.getUserRole().equals("부관리자"))
                .map(adminUser -> getUserByEmail(adminUser.getUserEmail()))
                .forEach(adminUser -> adminUser.updateRole(Role.MANAGER));

        editUsers.stream()
                .filter(editUser -> editUser.getUserRole().equals("일반 사용자"))
                .map(adminUser -> getUserByEmail(adminUser.getUserEmail()))
                .forEach(user -> user.updateRole(Role.USER));

    }

    public void editUserInfo(RequestUpdateUserInfo editUser) {
        User user = getUserByEmail(editUser.getUserEmail());
        user.updateUserInfo(editUser.getUserName());
    }

    private User getUserByEmail(String email) {
        return userRepository.getUserFromEmail(email)
                .orElseThrow(NullUserException::new);
    }
}
