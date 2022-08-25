package toyproject.blogawspractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.blogawspractice.domain.category.Category;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.exception.NullPostException;
import toyproject.blogawspractice.exception.NullUserException;
import toyproject.blogawspractice.repository.category.CategoryRepository;
import toyproject.blogawspractice.repository.post.PostRepository;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.web.request.post.PostSearch;
import toyproject.blogawspractice.web.request.post.RequestAddPost;
import toyproject.blogawspractice.web.request.post.RequestEditPost;
import toyproject.blogawspractice.web.response.post.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static toyproject.blogawspractice.config.auth.logic.FindEmailByOAuth2User.findEmail;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // TODO:Id를 반환하는 게 나을까? 객체를 반환하는 게 나을까? 저장 후 바로 글이 조회가 되어야 하는데, 이럴 때 어떻게 해야할지 고민?
    // 저장
    public ResponsePost savePost(RequestAddPost requestAddPost, OAuth2User user) throws NullUserException {
        Post post = postRepository.save(requestAddPost.toEntity());
        String categoryName = requestAddPost.getCategoryName();

        String email = findEmail(user);
        User findUser = userRepository.getUserFromEmail(email)
                .orElseThrow(NullUserException::new);

        if (!categoryName.isEmpty()) {
            Category category = categoryRepository.findByName(categoryName);

            post.addCategory(category);
            category.addPost(post);
        }

        post.addUser(findUser);
        findUser.addPost(post);

        return new ResponsePost(post);
    }

    public void sessionTest(OAuth2User user) {
        String email = findEmail(user);
        log.info("현재 유저의 email 주소 : {}", email);
    }

    // 단건 조회 - for edit
    @Transactional(readOnly = true)
    public ResponsePost readPost(Long id) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(NullPostException::new);

        return new ResponsePost(post);
    }

    // 단건 조회 - 오버로딩 - for 단건조회 페이지
    @Transactional(readOnly = true)
    public ResponsePost readPost(Long id, OAuth2User oAuth2User) throws NullPostException {
        Post post = postRepository.findById(id)
                .orElseThrow(NullPostException::new);

        String postUserEmail = post.getUser().getUserEmail();
        String oAuth2UserEmail = findEmail(oAuth2User);

        if (post.getViews() == null) {
            post.initViews();
        }

        // TODO: 나중에는 단시간 내에 너무 많은 조회수가 발생하면 어떻게 처리해야 할까 고민해보자.
        if (!postUserEmail.equals(oAuth2UserEmail)) {
            post.updateViews(1);
        }

        return new ResponsePost(post);
    }

    // 여러건 조회 및 페이징
    @Transactional(readOnly = true)
    public List<ResponsePost> getPostList(PostSearch postSearch) {
        return postRepository.getPostList(postSearch).stream()
                .map(ResponsePost::new)
                .collect(Collectors.toList());
    }

    // 페이지 개수 조회 TODO: 다른 사람도 로직을 잘 알아볼 수 있게 수정해보자..
    @Transactional(readOnly = true)
    public List<Integer> getPageCount(PostSearch postSearch) {
        Integer totalPostNumber = postRepository.findAll().size();
        Integer pageSize = postSearch.getSize();

        int pageCount = totalPostNumber / pageSize + 1;

        return IntStream.range(1, pageCount + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    // 수정
    public ResponsePost editPost(Long id, RequestEditPost editPost, OAuth2User user) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(NullPostException::new);

        String categoryName = editPost.getCategoryName();

        String email = findEmail(user);

        User findUser = userRepository.getUserFromEmail(email)
                .orElseThrow(NullUserException::new);

        if (post.getUser() == findUser) {
            if (!categoryName.isEmpty()) {  // 카테고리 수정 로직. 카테고리 값이 빈값이 아니어야만 수행됨.
                Category category = categoryRepository.findByName(categoryName);

                post.addCategory(category);
                category.addPost(post);
            } else {
                post.addCategory(null);  // 카테고리 값이 비었으면 카테고리에 null 할당.
            }

            post.edit(editPost);  // 카테고리를 제외한 나머지 수정

            return new ResponsePost(post);
        } else {
            throw new IllegalAccessException("잘못된 접근입니다.");
        }
    }

    // 삭제
    public Long deletePost(Long id, OAuth2User user) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(NullPostException::new);

        String email = findEmail(user);

        User findUser = userRepository.getUserFromEmail(email)
                .orElseThrow(NullUserException::new);

        if ((post.getUser() == findUser) || (findUser.getUserRole().equals(Role.ADMIN))) {
            postRepository.delete(post);
            return id;
        } else {
            throw new IllegalAccessException("잘못된 접근입니다.");  // 글 작성자가 아닌 사람이 글을 지우려 할 때.
        }
    }


}