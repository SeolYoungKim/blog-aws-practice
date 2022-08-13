package toyproject.blogawspractice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.web.request.category.RequestAddCategory;
import toyproject.blogawspractice.web.request.category.RequestEditCategory;
import toyproject.blogawspractice.web.response.category.ResponseCategory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryServiceTest {

    @Autowired CategoryService categoryService;
    @Autowired CategoryRepository categoryRepository;

    @BeforeEach
    void clear() {
        categoryRepository.deleteAll();
    }

    @DisplayName("카테고리가 저장된다.")
    @Test
    void saveCategory() {
        RequestAddCategory category = RequestAddCategory.builder()
                .name("category1")
                .build();

        ResponseCategory responseCategory = categoryService.saveCategory(category);

        List<Category> findCategory = categoryRepository.findAll();

        assertThat(findCategory.size()).isEqualTo(1);
    }

    @DisplayName("카테고리가 조회된다.")
    @Test
    void readCategory() throws NullPostException {
        Category category = Category.builder()
                .name("category1")
                .build();

        categoryRepository.save(category);

        ResponseCategory responseCategory = categoryService.readCategory(category.getId());

        assertThat(responseCategory.getName()).isEqualTo("category1");
    }

    @DisplayName("카테고리가 여러건 조회된다.")
    @Test
    void getCategoryList() {
        List<Category> categoryList = IntStream.range(1, 11)
                .mapToObj(i -> Category.builder()
                        .name("category" + i)
                        .build())
                .collect(Collectors.toList());

        categoryRepository.saveAll(categoryList);

        List<ResponseCategory> responseCategories = categoryService.getCategoryList();
        assertThat(responseCategories.get(0).getName()).isEqualTo("category1");
        assertThat(responseCategories.get(9).getName()).isEqualTo("category10");
    }

    @Transactional
    @DisplayName("카테고리가 수정된다.")
    @Test
    void editCategory() throws NullPostException {
        Category category = Category.builder()
                .name("category1")
                .build();

        categoryRepository.save(category);

        RequestEditCategory editCategory = RequestEditCategory.builder()
                .name("카테고리입니다.")
                .build();

        categoryService.editCategory(category.getId(), editCategory);

        assertThat(category.getName()).isEqualTo("카테고리입니다.");
    }

    @DisplayName("카테고리가 삭제된다.")
    @Test
    void deleteCategory() throws NullPostException {
        Category category = Category.builder()
                .name("category1")
                .build();

        categoryRepository.save(category);

        Long categoryId = category.getId();

        Long id = categoryService.deleteCategory(category.getId());

        assertThat(id).isEqualTo(categoryId);
        assertThat(categoryRepository.findById(categoryId).orElse(null)).isNull();
    }
}