package toyproject.blogawspractice.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.blogawspractice.service.UserService;
import toyproject.blogawspractice.web.request.user.RequestEditUser;
import toyproject.blogawspractice.web.request.user.RequestUpdateUserInfo;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PatchMapping("/user/edit")
    public void roleUpdate(@RequestBody List<RequestEditUser> editUsers) {
        userService.editRole(editUsers);
    }

    @PatchMapping("/setting/edit")
    public void userInfoUpdate(@RequestBody RequestUpdateUserInfo editUser) {
        userService.editUserInfo(editUser);
    }
}
