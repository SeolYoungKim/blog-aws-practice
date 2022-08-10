package toyproject.blogawspractice.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.request.post.RequestAddPost;
import toyproject.blogawspractice.web.request.post.RequestEditPost;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    // TODO: API에서, 무엇을 반환할지는 항상 고민해봐야 할 문제이다.
    @PostMapping("/write")
    public ResponsePost writePost(@Validated @RequestBody RequestAddPost requestAddPost) {
        return postService.savePost(requestAddPost);
    }

    @GetMapping("/api/post/{id}")
    public ResponsePost readPost(@PathVariable Long id) throws NullPostException {
        return postService.readPost(id);
    }

    @GetMapping("/api/posts")  // 요청 파라미터로 page와 size를 받는다.
    public List<ResponsePost> getPostList(@ModelAttribute PostSearch postSearch) {
        return postService.getPostList(postSearch);
    }

    @PatchMapping("/api/post/{id}/edit")
    public ResponsePost editPost(@PathVariable Long id, @Validated @RequestBody RequestEditPost editPost) throws NullPostException {
        return postService.editPost(id, editPost);
    }

    @DeleteMapping("/post/{id}/delete")
    public Long deletePost(@PathVariable Long id) throws NullPostException {
        return postService.deletePost(id);
    }
}
