package toyproject.blogawspractice.repository.post;

import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getPostList(PostSearch postSearch);

    List<Post> searchPostByTitle(String title);
    List<Post> searchPostByContent(String content);
}
