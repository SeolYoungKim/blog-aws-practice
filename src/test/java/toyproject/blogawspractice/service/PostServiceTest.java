package toyproject.blogawspractice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @DisplayName("전체 포스트에 대한 페이지 개수를 구한다.")
    @Test
    void getPageCount() {
        List<Post> postList = IntStream.range(1, 17)
                .mapToObj(i -> Post.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .author("author" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(postList);

        PostSearch postSearch = PostSearch.builder()
                .build();

        List<Integer> pageCount = postService.getPageCount(postSearch);

        assertThat(pageCount.size()).isEqualTo(4);
    }
}