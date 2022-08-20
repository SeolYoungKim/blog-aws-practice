package toyproject.blogawspractice.web.response.user;

import lombok.Getter;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.User;

import java.util.List;

@Getter
public class ResponseUser {
    private final String username;
    private final String userEmail;
    private final String userRole;
    private final List<Post> postList;

    public ResponseUser(User user) {
        username = user.getUsername();
        userEmail = user.getUserEmail();
        userRole = user.getUserRole().getTitle();
        postList = user.getPostList();
    }
}
