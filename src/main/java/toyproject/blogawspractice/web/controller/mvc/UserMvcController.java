package toyproject.blogawspractice.web.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.blogawspractice.service.UserService;
import toyproject.blogawspractice.web.response.user.ResponseUser;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserMvcController {

    private final UserService userService;

    @GetMapping("/setting")
    public String readUser(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        ResponseUser user = userService.readUser(oAuth2User);
        model.addAttribute("user", user);

        return "user/setting";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<ResponseUser> allUser = userService.findAllUser();
        model.addAttribute("userList", allUser);

        return "user/admin";
    }
}
