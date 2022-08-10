package toyproject.blogawspractice.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.service.CategoryService;
import toyproject.blogawspractice.web.request.category.RequestAddCategory;
import toyproject.blogawspractice.web.request.category.RequestEditCategory;
import toyproject.blogawspractice.web.response.category.ResponseCategory;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    // 저장
    @PostMapping("/category/add")
    public ResponseCategory addCategory(@Validated @RequestBody RequestAddCategory addCategory) {
        return categoryService.saveCategory(addCategory);
    }

    // 수정
    @PatchMapping("/category/{id}/edit")
    public ResponseCategory editCategory(@PathVariable Long id, @Validated @RequestBody RequestEditCategory editCategory) throws NullPostException {
        return categoryService.editCategory(id, editCategory);
    }

    // 삭제
    @DeleteMapping("/category/{id}/delete")
    public Long deleteCategory(@PathVariable Long id) throws NullPostException {
        return categoryService.deleteCategory(id);
    }
}
