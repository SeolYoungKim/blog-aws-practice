package toyproject.blogawspractice.web.request.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import toyproject.blogawspractice.domain.category.Category;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RequestAddCategory {

    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String name;

    // 필드가 한개일 때는 @RequestBody를 통한 데이터 바인딩 진행 시, 기본생성자 위임을 제공해주지 않는다.
    // 필드가 한개일 때는 반드시 기본 생성자를 추가하자.
    public RequestAddCategory() {
    }

    @Builder
    public RequestAddCategory(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }
}
