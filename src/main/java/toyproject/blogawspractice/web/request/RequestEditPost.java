package toyproject.blogawspractice.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class RequestEditPost {
    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;


    @Builder
    public RequestEditPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
