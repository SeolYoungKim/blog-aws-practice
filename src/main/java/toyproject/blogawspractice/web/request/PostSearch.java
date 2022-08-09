package toyproject.blogawspractice.web.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Getter
@Setter
@Builder  // Builder.Default를 정상적으로 사용하려면 생성자에 Builder를 달아줘야 한다.
public class PostSearch {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 5;

    public PostSearch(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public int getOffset() {
        return (max(page, 1) - 1) * min(size, 2000);
    }
}
