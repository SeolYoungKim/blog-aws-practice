package toyproject.blogawspractice.repository.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.blogawspractice.domain.post.Post;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void clear() {
        postRepository.deleteAllInBatch();
    }

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
}