package toyproject.blogawspractice.repository.post;

import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.web.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    Post findByTitle(String title);

    Post findByContent(String content);

    Post findByAuthor(String author);

    List<Post> getPostList(PostSearch postSearch);
}
