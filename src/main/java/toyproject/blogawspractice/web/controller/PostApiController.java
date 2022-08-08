package toyproject.blogawspractice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.PostSearch;
import toyproject.blogawspractice.web.request.RequestAddPost;
import toyproject.blogawspractice.web.response.ResponsePost;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    // TODO: API에서, 무엇을 반환할지는 항상 고민해봐야 할 문제이다.
    @PostMapping("/write")
    public ResponsePost writePost(@RequestBody RequestAddPost requestAddPost) {
        return postService.savePost(requestAddPost);
    }

    @GetMapping("/post/{id}")
    public ResponsePost readPost(@PathVariable Long id) throws NullPostException {
        return postService.readPost(id);
    }

    @GetMapping("/posts")  // 요청 파라미터로 page와 size를 받는다.
    public List<ResponsePost> getPostList(@ModelAttribute PostSearch postSearch) {
        return postService.getPostList(postSearch);
    }
}
