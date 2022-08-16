package toyproject.blogawspractice.repository.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.config.auth.dto.SessionUser;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.request.post.RequestAddPost;
import toyproject.blogawspractice.web.response.post.ResponsePost;

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
                .build();

        postRepository.save(post);

        //when
        Post findPost = postRepository.findByContent("내용");

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
        Post findPost = postRepository.findByUser(user);

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

    @DisplayName("Post와 Category의 관계 매핑이 제대로 되었는지 확인")
    @Test
    void postCategoryMapping() {
        Category category = Category.builder()
                .name("카테고리")
                .build();

        categoryRepository.save(category);

        RequestAddPost post = RequestAddPost.builder()
                .title("제목")
                .content("내용")
                .categoryName("카테고리")
                .build();

        User user = User.builder()
                .username("name")
                .userEmail("email")
                .userPicture("picture")
                .userRole(Role.USER)
                .build();

        userRepository.save(user);

        SessionUser sessionUser = new SessionUser(user);

        ResponsePost responsePost = postService.savePost(post, sessionUser);

        Post post1 = postRepository.findById(responsePost.getId()).orElse(null);

        assertThat(post1.getCategory().getName()).isEqualTo("카테고리");
        assertThat(post1.getCategory().getId()).isEqualTo(category.getId());
        assertThat(category.getPostList().size()).isEqualTo(1);
        assertThat(category.getPostList().get(0)).isEqualTo(postRepository.findByTitle("제목"));
    }
}