package toyproject.blogawspractice.web.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import toyproject.blogawspractice.config.auth.LoginUser;
import toyproject.blogawspractice.config.auth.dto.SessionUser;
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
    public String readCategory(@PathVariable Long id, Model model,
                               @LoginUser SessionUser user) throws Exception {

        ResponseCategory responseCategory = categoryService.readCategory(id);
        model.addAttribute("category", responseCategory);
        model.addAttribute("user", user);

        return "category/read-category";
    }

    // 여러건 조회
    @GetMapping("/categories")
    public String getCategoryList(Model model, @LoginUser SessionUser user) {
        List<ResponseCategory> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("user", user);

        return "category/read-category-list";
    }
}
