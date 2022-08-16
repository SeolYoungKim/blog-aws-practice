package toyproject.blogawspractice.repository.post;

import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    Post findByTitle(String title);

    Post findByContent(String content);

    Post findByUser(User user);

    List<Post> getPostList(PostSearch postSearch);
}
