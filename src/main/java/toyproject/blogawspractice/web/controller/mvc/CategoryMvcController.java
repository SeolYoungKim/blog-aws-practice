package toyproject.blogawspractice.web.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import toyproject.blogawspractice.service.CategoryService;
import toyproject.blogawspractice.web.response.category.ResponseCategory;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryMvcController {

    private final CategoryService categoryService;

    @GetMapping("/category/add")
    public String addCategory() {
        return "category/add-category";
    }

    @GetMapping("/category/{id}/edit")
    public String editCategory(@PathVariable Long id, Model model) throws Exception {
        ResponseCategory responseCategory = categoryService.readCategory(id);
        model.addAttribute("category", responseCategory);
        return "category/edit-category";
    }

    // 단건 조회
    @GetMapping("/category/{id}")
    public String readCategory(@PathVariable Long id, Model model) throws Exception {
        ResponseCategory responseCategory = categoryService.readCategory(id);
        model.addAttribute("category", responseCategory);
        return "category/read-category";
    }

    // 여러건 조회
    @GetMapping("/categories")
    public String getCategoryList(Model model) {
        List<ResponseCategory> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "category/read-category-list";
    }
}
