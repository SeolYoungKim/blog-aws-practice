package toyproject.blogawspractice.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import toyproject.blogawspractice.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    //TODO: 유저 정보 수정 관련 컨트롤러 추후 구현
}
