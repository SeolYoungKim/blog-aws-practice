package toyproject.blogawspractice.web.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@Builder  // Builder.Default를 정상적으로 사용하려면 생성자에 Builder를 달아줘야 한다.
public class PostSearch {

    // test를 좀 더 편하게 하기위해 default 사용
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 5;
    @Builder.Default
    private String keyword = "";
    @Builder.Default
    private String type = "title";

    public PostSearch(Integer page, Integer size, String keyword, String type) {
        this.page = page;
        this.size = size;
        this.keyword = keyword;
        this.type = type;
    }

    //TODO: 이방법보다 더 나은 방법이 있을까?
    public int getOffset() {
        page = page == null? 1 : page;
        size = size == null? 5 : size;

        return (max(page, 1) - 1) * min(size, 2000);
    }
}
