package toyproject.blogawspractice.web.controller.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.post.PostRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: 재구성 필요.
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class PostMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

//    @DisplayName("수정 화면에 카테고리 셀렉트 박스가 추가되고 post에 카테고리가 있으면 해당 카테고리가 선택되어있다.")
//    @Test
//    void edit_categorySelectBox1() throws Exception {
//        List<Category> categories = IntStream.range(1, 4)
//                .mapToObj(i -> Category.builder()
//                        .name("목록" + i)
//                        .build())
//                .collect(Collectors.toList());
//
//        categoryRepository.saveAll(categories);
//
//        Post post = Post.builder()
//                .title("제목")
//                .content("내용")
//                .author("저자")
//                .category(categories.get(0))
//                .build();
//
//        postRepository.save(post);
//
//        mockMvc.perform(get("/post/{id}/edit", post.getId())
//                        .contentType(TEXT_HTML))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("목록1")))
//                .andExpect(content().string(containsString("목록2")))
//                .andExpect(content().string(containsString("목록3")))
//                .andExpect(content().string(containsString("<option selected value=\"목록1\">")))
//                .andDo(print());
//    }

//    @DisplayName("수정 화면에 카테고리 셀렉트 박스가 추가되고 post에 카테고리가 없으면 '카테고리를 선택하세요'가 선택되어있다.")
//    @Test
//    void edit_categorySelectBox2() throws Exception {
//        List<Category> categories = IntStream.range(1, 4)
//                .mapToObj(i -> Category.builder()
//                        .name("목록" + i)
//                        .build())
//                .collect(Collectors.toList());
//
//        categoryRepository.saveAll(categories);
//
//        Post post = Post.builder()
//                .title("제목")
//                .content("내용")
//                .author("저자")
//                .build();
//
//        postRepository.save(post);
//
//        mockMvc.perform(get("/post/{id}/edit", post.getId())
//                        .contentType(TEXT_HTML))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("목록1")))
//                .andExpect(content().string(containsString("목록2")))
//                .andExpect(content().string(containsString("목록3")))
//                .andExpect(content().string(containsString("<option selected>카테고리를 선택하세요.</option>")))
//                .andDo(print());
//    }

    @DisplayName("카테고리가 있을 때는 카테고리명이 출력된다.")
    @Test
    void readPost_categoryOk() throws Exception {
        Category category = Category.builder()
                .name("목록1")
                .build();

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("저자")
                .category(category)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/post/{id}", post.getId())
                        .contentType(TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("목록1")))
                .andDo(print());
    }

//    @DisplayName("카테고리가 없을 때는 '카테고리가 없습니다.' 메시지가 출력된다.")
//    @Test
//    void readPost_NoCategory() throws Exception {
//
//        Post post = Post.builder()
//                .title("제목")
//                .content("내용")
//                .author("저자")
//                .build();
//
//        postRepository.save(post);
//
//        mockMvc.perform(get("/post/{id}", post.getId())
//                        .contentType(TEXT_HTML))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("카테고리가 없습니다.")))
//                .andDo(print());
//    }

}