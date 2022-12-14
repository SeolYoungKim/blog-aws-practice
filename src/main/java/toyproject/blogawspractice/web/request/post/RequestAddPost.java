package toyproject.blogawspractice.web.request.post;

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

//    @NotBlank(message = "저자를 입력해주세요.")
//    private String author;

    // 이거 그냥 카테고리 이름으로 받아서 처리해보자...
    private String categoryName;

    @Builder
    public RequestAddPost(String title, String content, String categoryName) {
        this.title = title;
        this.content = content;
//        this.author = author;
        this.categoryName = categoryName;
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
//                .author(this.author)
//                .category(this.category)
                .build();
    }
}
