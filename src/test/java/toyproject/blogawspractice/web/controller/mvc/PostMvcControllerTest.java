package toyproject.blogawspractice.web.controller.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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


    @DisplayName("?????? ????????? ???????????? ????????? ????????? ????????????.")
    @Test
    void write_categorySelectBox() throws Exception {
        List<Category> categories = IntStream.range(1, 4)
                .mapToObj(i -> Category.builder()
                        .name("??????" + i)
                        .build())
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);

        mockMvc.perform(get("/write")
                        .contentType(TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("??????1")))
                .andExpect(content().string(containsString("??????2")))
                .andExpect(content().string(containsString("??????3")))
                .andDo(print());
    }

//    @DisplayName("??????????????? ?????? ?????? ?????????????????? ????????????.")
//    @Test
    void readPost_categoryOk() throws Exception {
        Category category = Category.builder()
                .name("??????1")
                .build();


        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("??????")
                .content("??????")
                .category(category)
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/post/{id}", post.getId())
                        .contentType(TEXT_HTML)
                        .with(oauth2Login().attributes(attr -> attr.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("??????1")))
                .andDo(print());
    }

//    @DisplayName("?????? ????????? ????????? ????????? ????????? ???????????? ???????????? ????????? ?????????.")
//    @Test
    void noUpdateViews() throws Exception {
        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("??????")
                .content("??????")
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

//    @DisplayName("?????? ????????? ????????? ????????? ????????? ????????? ???????????? ?????????.")
//    @Test
    void updateViews() throws Exception {
        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("??????")
                .content("??????")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/post/{id}", post.getId())
                        .contentType(TEXT_HTML)
                        .with(oauth2Login()
                                .attributes(attrs -> attrs.put("email", "other-email"))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name=\"views\" value=\"1\">")))
                .andDo(print());
    }

}