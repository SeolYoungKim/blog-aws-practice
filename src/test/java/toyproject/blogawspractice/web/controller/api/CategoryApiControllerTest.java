package toyproject.blogawspractice.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.service.CategoryService;
import toyproject.blogawspractice.web.request.category.RequestAddCategory;
import toyproject.blogawspractice.web.request.category.RequestEditCategory;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "USER")
class CategoryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("요청 시 카테고리가 저장된다.")
    @Test
    void addCategory() throws Exception {
        RequestAddCategory category = RequestAddCategory.builder()
                .name("category1")
                .build();

        mockMvc.perform(post("/category/add")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("category1"))
                .andDo(print());
    }

    @DisplayName("요청 시 카테고리가 수정된다.")
    @Test
    void editCategory() throws Exception {

        Category category = Category.builder()
                .name("category")
                .build();

        categoryRepository.save(category);

        RequestEditCategory editCategory = RequestEditCategory.builder()
                .name("카테고리입니다.")
                .build();

        mockMvc.perform(patch("/category/{id}/edit", category.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("카테고리입니다."))
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andDo(print());
    }

    @DisplayName("요청 시 카테고리가 삭제된다.")
    @Test
    void deleteCategory() throws Exception {
        Category category = Category.builder()
                .name("category")
                .build();

        categoryRepository.save(category);

        mockMvc.perform(delete("/category/{id}/delete", category.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(category.getId())))
                .andDo(print());
    }
}