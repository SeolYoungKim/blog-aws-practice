package toyproject.blogawspractice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @DisplayName("글을 저장한다.")
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
                .title("제목")
                .content("내용")
                .categoryName("")
                .build();

        //then
        mockMvc.perform(post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post))
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andDo(print());
    }

    @DisplayName("글 단건 조회")
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
                .title("단건조회 제목")
                .content("단건조회 내용")
                .user(user)
                .build();

        //when
        Post save = postRepository.save(post);

        //then
        mockMvc.perform(get("/api/post/{id}", save.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("단건조회 제목"))
                .andExpect(jsonPath("$.content").value("단건조회 내용"))
                .andDo(print());
    }

    @DisplayName("잘못된 ID 조회하면 NullPostException이 발생한다")
    @Test
    void read_post_error() throws Exception {

        mockMvc.perform(get("/api/post/{id}", "12323")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("조회할 글이 없습니다."))
                .andDo(print());
    }

    @DisplayName("페이징 처리가 잘 되었는지 확인한다.")
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

    @DisplayName("검증이 제대로 작동하는지")
    @Test
    void validate_post() throws Exception {
        RequestAddPost requestAddPost = RequestAddPost.builder()
                .title("제목")
                .content("")
                .build();

        mockMvc.perform(post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAddPost)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[0].code").value("404"))
                .andExpect(jsonPath("$.[0].message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.[0].errors.default_message").value("내용을 입력해주세요."))
                .andExpect(jsonPath("$.[0].errors.field").value("content"))
                .andDo(print());
    }

    @DisplayName("글 수정")
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
                .title("제목")
                .content("내용")
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

    @DisplayName("글 삭제")
    @Test
    void delete_post() throws Exception {

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

        mockMvc.perform(delete("/post/{id}/delete", post.getId())
                        .contentType(APPLICATION_JSON)
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "email"))))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(post.getId())))
                .andDo(print());
    }
}