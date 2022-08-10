package toyproject.blogawspractice.web.request.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class RequestEditCategory {

    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String name;

    public RequestEditCategory() {
    }

    @Builder
    public RequestEditCategory(String name) {
        this.name = name;
    }

}
