package toyproject.blogawspractice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.request.post.RequestAddPost;
import toyproject.blogawspractice.web.request.post.RequestEditPost;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "USER")
class PostApiControllerTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("?????? ????????????.")
    @Test
    void save_post() throws Exception {
        //given
        User user1 = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user1);

        RequestAddPost post = RequestAddPost.builder()
                .title("??????")
                .content("??????")
                .categoryName("")
                .build();

        //then
        mockMvc.perform(post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post))
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("??????"))
                .andExpect(jsonPath("$.content").value("??????"))
                .andDo(print());

        assertThat(postRepository.findAll().size()).isEqualTo(1);  // db??? ???????????? ??????
    }

    @DisplayName("??? ?????? ??????")
    @Test
    void read_post() throws Exception {
        //given
        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("???????????? ??????")
                .content("???????????? ??????")
                .user(user)
                .build();

        //when
        Post save = postRepository.save(post);

        //then
        mockMvc.perform(get("/api/post/{id}", save.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("???????????? ??????"))
                .andExpect(jsonPath("$.content").value("???????????? ??????"))
                .andDo(print());
    }

    @DisplayName("????????? ID ???????????? NullPostException??? ????????????")
    @Test
    void read_post_error() throws Exception {

        mockMvc.perform(get("/api/post/{id}", "12323")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("????????? ?????? ????????????."))
                .andDo(print());
    }

    @DisplayName("????????? ????????? ??? ???????????? ????????????.")
    @Test
    void get_post_list() throws Exception {

        User user = User.builder()
                .userRole(Role.USER)
                .userPicture("picture")
                .userEmail("email")
                .username("kim")
                .build();

        userRepository.save(user);

        //given
        List<Post> posts = IntStream.range(1, 21)
                .mapToObj(i -> Post.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(posts);

        PostSearch postSearch = PostSearch.builder()
                .build();

        mockMvc.perform(get("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("title20"))
                .andExpect(jsonPath("$.[0].content").value("content20"))
                .andDo(print());
    }

    @DisplayName("????????? ????????? ???????????????")
    @Test
    void validate_post() throws Exception {
        RequestAddPost requestAddPost = RequestAddPost.builder()
                .title("??????")
                .content("")
                .build();

        mockMvc.perform(post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAddPost)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].code").value("404"))
                .andExpect(jsonPath("$.[0].message").value("????????? ???????????????."))
                .andExpect(jsonPath("$.[0].errors.default_message").value("????????? ??????????????????."))
                .andExpect(jsonPath("$.[0].errors.field").value("content"))
                .andDo(print());
    }

    @DisplayName("??? ??????")
    @Test
    void edit_post() throws Exception {

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

        RequestEditPost editPost = RequestEditPost.builder()
                .title("TITLE")
                .content("CONTENT")
                .categoryName("")
                .build();

        mockMvc.perform(patch("/api/post/{id}/edit", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editPost))
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("TITLE"))
                .andExpect(jsonPath("$.content").value("CONTENT"))
                .andExpect(jsonPath("$.userName").value("kim"))
                .andDo(print());
    }

    @DisplayName("??? ????????? ????????? ??? ??? ??????.")
    @Test
    void edit_post_other() throws Exception {

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

        User another = User.builder()
                .userRole(Role.ADMIN)
                .userPicture("another")
                .userEmail("another")
                .username("another")
                .build();

        userRepository.save(another);

        RequestEditPost editPost = RequestEditPost.builder()
                .title("TITLE")
                .content("CONTENT")
                .categoryName("")
                .build();

        mockMvc.perform(patch("/api/post/{id}/edit", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editPost))
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "another"))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isBadRequest())
                .andDo(print());

//        assertThatThrownBy(() -> mockMvc.perform(patch("/api/post/{id}/edit", post.getId())
//                .contentType(APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(editPost))
//                .with(oauth2Login().attributes(attrs -> attrs.put("email", "another"))
//                        .authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))))
//                .hasCause(new IllegalAccessException("????????? ???????????????."));

    }

    @DisplayName("??? ????????? ????????? ??? ??? ??????.")
    @Test
    void delete_post_owner() throws Exception {

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

        mockMvc.perform(delete("/post/{id}/delete", post.getId())
                        .contentType(APPLICATION_JSON)
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(post.getId())))
                .andDo(print());
    }

    @DisplayName("????????? ??? ????????? ???????????? ??? ??? ??????.")
    @Test
    void delete_post_admin() throws Exception {

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

        User admin = User.builder()
                .userRole(Role.ADMIN)
                .userPicture("pic")
                .userEmail("admin")
                .username("kim")
                .build();

        userRepository.save(admin);


        mockMvc.perform(delete("/post/{id}/delete", post.getId())
                        .contentType(APPLICATION_JSON)
                        .with(oauth2Login().attributes(attr -> attr.put("email", "admin"))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))  // ?????? ?????? ????????? ????????? ????????? ????????? ???????????? ????????? ????????????, ??????????????? ???????????? ????????? USER??????.. ????????????.
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(post.getId())))
                .andDo(print());
    }

    @DisplayName("????????? ??? ????????? ??????????????? ??? ??? ??????.")
    @Test
    void delete_post_manager() throws Exception {

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

        User admin = User.builder()
                .userRole(Role.MANAGER)
                .userPicture("pic")
                .userEmail("manager")
                .username("kim")
                .build();

        userRepository.save(admin);


        mockMvc.perform(delete("/post/{id}/delete", post.getId())
                        .contentType(APPLICATION_JSON)
                        .with(oauth2Login().attributes(attr -> attr.put("email", "manager"))
                                .authorities(new SimpleGrantedAuthority("ROLE_MANAGER"))))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(post.getId())))
                .andDo(print());
    }
}