package toyproject.blogawspractice.web.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import toyproject.blogawspractice.config.auth.LoginUser;
import toyproject.blogawspractice.config.auth.dto.SessionUser;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.service.CategoryService;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.response.category.ResponseCategory;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostMvcController {

    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, @LoginUser SessionUser user) {  // 로그인 화면 구성 - 로그인 버튼

        if (user != null) {
            model.addAttribute("userName", user.getUserName());
            model.addAttribute("userRole", user.getUserRole());
        }

        return "index";
    }

    @GetMapping("/write")
    public String write(Model model) {
        List<ResponseCategory> categoryList = categoryService.getCategoryList();
        ResponsePost responsePost = new ResponsePost();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("post", responsePost); // 뷰에서 사용하기 위해 빈 객체 넘겨줌,.
        return "write-post";
    }

    @GetMapping("/post/{id}")
    public String readPost(@PathVariable Long id, Model model) throws NullPostException {
        ResponsePost responsePost = postService.readPost(id);
        model.addAttribute("post", responsePost);

        return "read-post";
    }

    @GetMapping("/posts")
    public String getPostList(@ModelAttribute PostSearch postSearch, Model model) {
        List<ResponsePost> postList = postService.getPostList(postSearch);
        List<Integer> pageCounts = postService.getPageCount(postSearch);

        model.addAttribute("postList", postList);
        model.addAttribute("postSearch", postSearch);
        model.addAttribute("postPageUpperLimit", postSearch.getPage() + 2);
        model.addAttribute("postPageLowerLimit", pageCounts.size() - 5);
        model.addAttribute("pageCounts", pageCounts);

        return "read-post-list";
    }

    @GetMapping("/post/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) throws NullPostException {
        ResponsePost responsePost = postService.readPost(id);
        List<ResponseCategory> categoryList = categoryService.getCategoryList();

        model.addAttribute("post", responsePost);
        model.addAttribute("categoryList", categoryList);

        return "edit-post";
    }
}
