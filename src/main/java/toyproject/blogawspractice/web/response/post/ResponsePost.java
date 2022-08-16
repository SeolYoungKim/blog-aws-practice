package toyproject.blogawspractice.web.response.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogawspractice.domain.post.Post;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ResponsePost {

    private Long id;
    private String title;
    private String content;
//    private String author;
//    private Category category;  //TODO: 순환참조 발생 이유가 여기였네..
    private String categoryName;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //TODO: 정녕 이렇게밖에 할 수 없는가? 필드가 더 많아진다면 너무 극혐이다. 분명 방법이 있을 것.
    public ResponsePost(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
//        this.author = post.getAuthor();
//        this.category = post.getCategory();
        this.categoryName = post.getCategory() != null? post.getCategory().getName() : "";
        this.userName = post.getUser().getUsername();
        this.modifiedDate = post.getModifiedDate();
        this.createdDate = post.getCreatedDate();
    }
}
