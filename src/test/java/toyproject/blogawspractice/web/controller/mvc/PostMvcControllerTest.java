package toyproject.blogawspractice.web.controller.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "USER")
class PostMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }


    @DisplayName("작성 화면에 카테고리 셀렉트 박스가 추가된다.")
    @Test
    void write_categorySelectBox() throws Exception {
        List<Category> categories = IntStream.range(1, 4)
                .mapToObj(i -> Category.builder()
                        .name("목록" + i)
                        .build())
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);

        mockMvc.perform(get("/write")
                        .contentType(TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("목록1")))
                .andExpect(content().string(containsString("목록2")))
                .andExpect(content().string(containsString("목록3")))
                .andDo(print());
    }

    @DisplayName("카테고리가 있을 때는 카테고리명이 출력된다.")
    @Test
    void readPost_categoryOk() throws Exception {
        Category category = Category.builder()
                .name("목록1")
                .build();


        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .category(category)
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/post/{id}", post.getId())
                        .contentType(TEXT_HTML)
                        .with(oauth2Login().attributes(attr -> attr.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("목록1")))
                .andDo(print());
    }

    @DisplayName("글을 작성한 유저와 조회한 유저가 동일하면 조회수가 오르지 않는다.")
    @Test
    void noUpdateViews() throws Exception {
        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/post/{id}", post.getId())
                        .contentType(TEXT_HTML)
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name=\"views\" value=\"0\">")))
                .andDo(print());
    }

    @DisplayName("글을 작성한 유저와 조회한 유저가 다르면 조회수가 오른다.")
    @Test
    void updateViews() throws Exception {
        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/post/{id}", post.getId())
                        .contentType(TEXT_HTML)
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "other-email"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name=\"views\" value=\"1\">")))
                .andDo(print());
    }

}