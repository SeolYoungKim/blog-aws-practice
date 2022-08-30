package toyproject.blogawspractice.web.request.user;

import lombok.Getter;
import lombok.Setter;

//TODO: 추후 제작(아직은 수정할 요소 없음. 유저에 소개 필드를 만들까?)
@Getter @Setter
public class RequestEditUser {
    private String userEmail;
    private String userRole;
}
