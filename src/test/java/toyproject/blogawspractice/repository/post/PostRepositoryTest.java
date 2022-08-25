package toyproject.blogawspractice.repository.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostService postService;

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
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByTitle("제목")
                .orElseThrow(NullPostException::new);

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
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByContent("내용")
                .orElseThrow(NullPostException::new);

        //then
        assertThat(post.getContent()).isEqualTo(findPost.getContent());

    }

    @DisplayName("User로 조회")
    @Test
    void findByAuthor() {
        //given
        User user = User.builder()
                .userPicture("picture")
                .userEmail("email")
                .userRole(Role.USER)
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByUser(user)
                .orElseThrow(NullPostException::new);

        //then
        assertThat(post.getUser()).isEqualTo(findPost.getUser());

    }

    @DisplayName("페이징 처리 + 여러건 조회")
    @Test
    void getPostList() {
        //given
        List<Post> posts = IntStream.range(1, 21)
                .mapToObj(i -> Post.builder()
                        .title("title" + i)
                        .content("content" + i)
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
                .build();

        postRepository.save(post);

        System.out.println(">>>>>>>>>>>" + post.getCreatedDate());
        System.out.println(">>>>>>>>>>>" + post.getModifiedDate());
    }

}