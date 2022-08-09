package toyproject.blogawspractice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.PostSearch;
import toyproject.blogawspractice.web.response.ResponsePost;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;

    @GetMapping("/")
    public String home() {  // 로그인 화면 구성 - 로그인 버튼
        return "index";
    }

    @GetMapping("/write")
    public String write() {
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
        model.addAttribute("postList", postList);

        return "read-post-list";
    }

    @GetMapping("/post/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) throws NullPostException {
        ResponsePost responsePost = postService.readPost(id);
        model.addAttribute("post", responsePost);

        return "edit-post";
    }
}
