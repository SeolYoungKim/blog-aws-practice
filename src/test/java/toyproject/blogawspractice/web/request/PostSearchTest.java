package toyproject.blogawspractice.web.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostSearchTest {

    @DisplayName("값을 안넣어줘도 기본 값이 세팅된다.")
    @Test
    void post_search_test() {
        //given
        PostSearch postSearch = PostSearch.builder()
                .build();

        //then
        assertThat(postSearch.getPage()).isEqualTo(1);
        assertThat(postSearch.getSize()).isEqualTo(5);
    }
}