package toyproject.blogawspractice.config.auth.dto;

import lombok.Getter;
import toyproject.blogawspractice.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private final String userName;
    private final String userEmail;
    private final String userPicture;

    public SessionUser(User user) {
        this.userName = user.getUsername();
        this.userEmail = user.getUserEmail();
        this.userPicture = user.getUserPicture();
    }
}
