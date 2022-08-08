package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.web.request.RequestAddPost;
import toyproject.blogawspractice.web.response.ResponsePost;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // Id를 반환하는 게 나을까? 객체를 반환하는 게 나을까? 저장 후 바로 글이 조회가 되어야 하는데, 이럴 때 어떻게 해야할지 고민?
    public ResponsePost savePost(RequestAddPost requestAddPost) {
        Post post = postRepository.save(requestAddPost.toEntity());
        return new ResponsePost(post);
    }



}

