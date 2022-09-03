package toyproject.blogawspractice.web.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUpdateUserInfo {
    private String userName;
    private String userEmail;

    @Builder
    public RequestUpdateUserInfo(String userEmail, String userName) {
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
