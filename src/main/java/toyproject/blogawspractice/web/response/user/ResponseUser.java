package toyproject.blogawspractice.web.response.user;

import lombok.Getter;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseUser {
    private final String username;
    private final String userEmail;
    private final String userRole;
    private final List<ResponsePost> postList;

    public ResponseUser(User user) {
        username = user.getUsername();
        userEmail = user.getUserEmail();
        userRole = user.getUserRole().getTitle();
        postList = user.getPostList().stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }
}
