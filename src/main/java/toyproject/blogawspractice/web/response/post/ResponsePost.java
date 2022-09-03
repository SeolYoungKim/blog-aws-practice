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
    private Integer views;

    //    private Category category;  //TODO: 순환참조 발생 이유가 여기였네..
    private String categoryName;

    private String userName;
    private String userRole;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //TODO: 정녕 이렇게밖에 할 수 없는가? 필드가 더 많아진다면 유지보수가 어려워질 것 같다.. 분명 방법이 있을 것.
    // 아니면, Response 객체도 목적에 따라 좀 나눌까... 고민해보자.
    public ResponsePost(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();

        this.categoryName = post.getCategory() != null? post.getCategory().getName() : "";

        this.userName = post.getUser().getUsername();
        this.userRole = post.getUser().getRoleKey();

        //TODO : 포맷을 변경
        this.modifiedDate = post.getModifiedDate();
        this.createdDate = post.getCreatedDate();
    }

    public ResponsePost markdownToHtml() {
        this.content = MarkdownParser.markdownToHTML(this.content);

        return this;
    }
}
