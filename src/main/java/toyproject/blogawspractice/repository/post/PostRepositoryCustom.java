package toyproject.blogawspractice.repository.post;

import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    Optional<Post> findByTitle(String title);

    Optional<Post> findByContent(String content);

    Optional<Post> findByUser(User user);

    List<Post> getPostList(PostSearch postSearch);
}
