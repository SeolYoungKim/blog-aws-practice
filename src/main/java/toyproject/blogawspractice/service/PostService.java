package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.request.post.RequestAddPost;
import toyproject.blogawspractice.web.request.post.RequestEditPost;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    // TODO:Id를 반환하는 게 나을까? 객체를 반환하는 게 나을까? 저장 후 바로 글이 조회가 되어야 하는데, 이럴 때 어떻게 해야할지 고민?
    // 저장
    public ResponsePost savePost(RequestAddPost requestAddPost) {
        Post post = postRepository.save(requestAddPost.toEntity());
        String categoryName = requestAddPost.getCategoryName();

        if (!categoryName.isEmpty()) {
            Category category = categoryRepository.findByName(categoryName);

            post.addCategory(category);
            category.addPost(post);
        }

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

    // 페이지 개수 조회
    public List<Integer> getPageCount(PostSearch postSearch) {
        Integer totalPostNumber = postRepository.findAll().size();
        Integer pageSize = postSearch.getSize();
        Integer pageCount = totalPostNumber / pageSize + 1;

        return IntStream.range(1, pageCount + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    // 수정
    public ResponsePost editPost(Long id, RequestEditPost editPost) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        String categoryName = editPost.getCategoryName();

        if (!categoryName.isEmpty()) {  // 카테고리 수정 로직. 카테고리 값이 빈값이 아니어야만 수행됨.
            Category category = categoryRepository.findByName(categoryName);

            post.addCategory(category);
            category.addPost(post);
        } else {
            post.addCategory(null);
        }

        post.edit(editPost);  // 카테고리를 제외한 나머지 수정

        return new ResponsePost(post);
    }

    // 삭제
    public Long deletePost(Long id) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NullPostException("글이 없습니다."));

        postRepository.delete(post);

        return id;
    }

//    @PostConstruct
//    public void sampleData() {
//        List<Post> postList = IntStream.range(1, 101)
//                .mapToObj(i -> Post.builder()
//                        .title("title" + i)
//                        .content("content" + i)
//                        .author("author" + i)
//                        .build())
//                .collect(Collectors.toList());
//
//        postRepository.saveAll(postList);
//    }

}