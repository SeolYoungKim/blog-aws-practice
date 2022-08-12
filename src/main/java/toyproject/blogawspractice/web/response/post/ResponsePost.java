package toyproject.blogawspractice.web.response.post;

import lombok.Getter;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;

import java.time.LocalDateTime;

@Getter
public class ResponsePost {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final Category category;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    //TODO: 정녕 이렇게밖에 할 수 없는가? 필드가 더 많아진다면 너무 극혐이다. 분명 방법이 있을 것.
    public ResponsePost(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.category = post.getCategory();
        this.modifiedDate = post.getModifiedDate();
        this.createdDate = post.getCreatedDate();
    }
}