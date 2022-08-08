package toyproject.blogawspractice.repository.post;

import toyproject.blogawspractice.domain.post.Post;

import java.util.List;

public interface PostRepositoryCustom {

    Post findByTitle(String title);

    Post findByContent(String content);

    Post findByAuthor(String author);

    //TODO: Paging을 위한 메서드 추가
}
