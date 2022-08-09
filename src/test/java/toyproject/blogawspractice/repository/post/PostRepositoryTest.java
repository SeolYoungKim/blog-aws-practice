package toyproject.blogawspractice.repository.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.web.request.PostSearch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

//    @AfterEach
//    void clear() {
//        postRepository.deleteAllInBatch();
//    }

    @DisplayName("title로 조회")
    @Test
    void findByTitle() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("저자")
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByTitle("제목");

        //then
        assertThat(post.getTitle()).isEqualTo(findPost.getTitle());
    }

    @DisplayName("content 로 조회")
    @Test
    void findByContent() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("저자")
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByContent("내용");

        //then
        assertThat(post.getContent()).isEqualTo(findPost.getContent());

    }

    @DisplayName("Author로 조회")
    @Test
    void findByAuthor() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("저자")
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByAuthor("저자");

        //then
        assertThat(post.getAuthor()).isEqualTo(findPost.getAuthor());

    }

    @DisplayName("페이징 처리 + 여러건 조회")
    @Test
    void getPostList() {
        //given
        List<Post> posts = IntStream.range(1, 21)
                .mapToObj(i -> Post.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .author("author" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(posts);

        PostSearch postSearch = PostSearch.builder().build();

        //when
        List<Post> postList = postRepository.getPostList(postSearch);

        //then
        assertThat(postList.get(0).getTitle()).isEqualTo("title20");

    }

    @DisplayName("modifiedDate 조회")
    @Test
    void timeCheck() {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("저자")
                .build();

        postRepository.save(post);

        System.out.println(">>>>>>>>>>>" + post.getCreatedDate());
        System.out.println(">>>>>>>>>>>" + post.getModifiedDate());
    }

}