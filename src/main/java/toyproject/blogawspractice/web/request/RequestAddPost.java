package toyproject.blogawspractice.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import toyproject.blogawspractice.domain.post.Post;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class RequestAddPost {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "저자를 입력해주세요.")
    private String author;

    @Builder
    public RequestAddPost(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .build();
    }
}
