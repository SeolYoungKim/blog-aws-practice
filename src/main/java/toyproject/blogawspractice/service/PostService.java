package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.web.request.PostSearch;
import toyproject.blogawspractice.web.request.RequestAddPost;
import toyproject.blogawspractice.web.response.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;

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

    // 단건 조회
    public ResponsePost readPost(Long id) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        return new ResponsePost(post);
    }

    // 여러건 조회 및 페이징
    public List<ResponsePost> getPostList(PostSearch postSearch) {
        return postRepository.getPostList(postSearch).stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }
}

