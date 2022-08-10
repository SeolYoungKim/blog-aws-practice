package toyproject.blogawspractice.web.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import toyproject.blogawspractice.domain.category.Category;
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

    private Category category;

    @Builder
    public RequestAddPost(String title, String content, String author, Category category) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .category(this.category)
                .build();
    }
}
