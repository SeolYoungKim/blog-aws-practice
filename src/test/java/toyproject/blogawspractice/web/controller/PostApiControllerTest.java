package toyproject.blogawspractice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.RequestAddPost;
import toyproject.blogawspractice.web.response.ResponsePost;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class PostApiControllerTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clear() {
        postRepository.deleteAllInBatch();
    }

    @DisplayName("글을 저장한다.")
    @Test
    void save_post() throws Exception {
        //given
        RequestAddPost post = RequestAddPost.builder()
                .title("제목")
                .content("내용")
                .author("저자")
                .build();

        //when
        postService.savePost(post);

        //then
        mockMvc.perform(post("/write")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.author").value("저자"))
                .andDo(print());
    }

    @DisplayName("글 단건 조회")
    @Test
    void read_post() throws Exception {
        //given
        RequestAddPost post = RequestAddPost.builder()
                .title("단건조회 제목")
                .content("단건조회 내용")
                .author("단건조회 저자")
                .build();

        //when
        ResponsePost responsePost = postService.savePost(post);

        //then
        mockMvc.perform(get("/post/{id}", responsePost.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("단건조회 제목"))
                .andExpect(jsonPath("$.content").value("단건조회 내용"))
                .andExpect(jsonPath("$.author").value("단건조회 저자"))
                .andDo(print());
    }

    @DisplayName("잘못된 ID 조회하면 NullPostException이 발생한다")
    @Test
    void read_post_error() throws Exception {

        mockMvc.perform(get("/post/{id}", "12323")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("글이 없습니다."))
                .andDo(print());
    }
}