package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.request.post.RequestAddPost;
import toyproject.blogawspractice.web.request.post.RequestEditPost;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // TODO:Id를 반환하는 게 나을까? 객체를 반환하는 게 나을까? 저장 후 바로 글이 조회가 되어야 하는데, 이럴 때 어떻게 해야할지 고민?
    // 저장
    public ResponsePost savePost(RequestAddPost requestAddPost) {
        Post post = postRepository.save(requestAddPost.toEntity());
        return new ResponsePost(post);
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public ResponsePost readPost(Long id) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        return new ResponsePost(post);
    }

    // 여러건 조회 및 페이징
    @Transactional(readOnly = true)
    public List<ResponsePost> getPostList(PostSearch postSearch) {
        return postRepository.getPostList(postSearch).stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }

    // 수정
    public ResponsePost editPost(Long id, RequestEditPost editPost) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        post.edit(editPost);

        return new ResponsePost(post);
    }

    // 삭제
    public Long deletePost(Long id) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        postRepository.delete(post);

        return id;
    }

}