package toyproject.blogawspractice.web.controller.mvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

@Slf4j
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
    public String readPost(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal OAuth2User oAuth2User,
                           @LoginUser SessionUser user) throws NullPostException {
        ResponsePost responsePost = postService.readPost(id, oAuth2User);
        model.addAttribute("post", responsePost);
        model.addAttribute("user", user);

        return "read-post";
    }

    // TODO: 어디서는 SessionUser, 어디서는 OAuth2User를 사용하고 있다. 테스트의 문제로 이렇게 사용하고 있지만, 획일화 할 필요가 있을까?
    @GetMapping("/posts")
    public String getPostList(@ModelAttribute PostSearch postSearch, Model model,
                              @LoginUser SessionUser user) {
        List<ResponsePost> postList = postService.getPostList(postSearch);
        List<Integer> pageCounts = postService.getPageCount(postSearch);

        //TODO: 여기 정리가 필요하다. 리팩토링 대상!
        model.addAttribute("user", user);

        model.addAttribute("postList", postList);
        model.addAttribute("postSearch", postSearch);

        model.addAttribute("pageCounts", pageCounts);
        model.addAttribute("postPageUpperLimit", postSearch.getPage() + 2);  // 특정 개수 이상은 버튼 노출 방식이 다름
        model.addAttribute("postPageLowerLimit", pageCounts.size() - 5);  //

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
