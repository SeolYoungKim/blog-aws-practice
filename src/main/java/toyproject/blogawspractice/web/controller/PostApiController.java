package toyproject.blogawspractice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.blogawspractice.service.PostService;
import toyproject.blogawspractice.web.request.RequestAddPost;
import toyproject.blogawspractice.web.response.ResponsePost;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("/write")
    public ResponsePost writePost(@RequestBody RequestAddPost requestAddPost) {
        return postService.savePost(requestAddPost);
    }
}
