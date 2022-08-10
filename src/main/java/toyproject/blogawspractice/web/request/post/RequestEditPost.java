package toyproject.blogawspractice.web.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import toyproject.blogawspractice.domain.category.Category;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class RequestEditPost {

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    private final Category category;

    @Builder
    public RequestEditPost(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
