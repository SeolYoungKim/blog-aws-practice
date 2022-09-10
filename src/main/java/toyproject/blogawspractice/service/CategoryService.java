package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.exception.NullCategoryException;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.web.request.category.RequestAddCategory;
import toyproject.blogawspractice.web.request.category.RequestEditCategory;
import toyproject.blogawspractice.web.response.category.ResponseCategory;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 저장
    public ResponseCategory saveCategory(RequestAddCategory addCategory) {
        Category category = categoryRepository.save(addCategory.toEntity());
        return new ResponseCategory(category);
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public ResponseCategory readCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(NullCategoryException::new);

        return new ResponseCategory(category);
    }

    // 여러건 조회
    @Transactional(readOnly = true)
    public List<ResponseCategory> getCategoryList() {
        return categoryRepository.findAll().stream()
                .map(ResponseCategory::new)
                .collect(Collectors.toList());

    }

    // 수정
    public ResponseCategory editCategory(Long id, RequestEditCategory editCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(NullCategoryException::new);

        category.edit(editCategory);

        return new ResponseCategory(category);
    }

    // 삭제
    public Long deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(NullCategoryException::new);

        categoryRepository.delete(category);

        return id;
    }
}
