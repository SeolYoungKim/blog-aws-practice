package toyproject.blogawspractice.web.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestEditUser {
    private String userEmail;
    private String userRole;

    @Builder
    public RequestEditUser(String userEmail, String userRole) {
        this.userEmail = userEmail;
        this.userRole = userRole;
    }
}
