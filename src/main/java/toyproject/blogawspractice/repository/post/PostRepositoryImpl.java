package toyproject.blogawspractice.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;

import static toyproject.blogawspractice.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: title, content는 검색 로직에 활용될 수 있도록 "일치" -> "포함"으로 변경 예정

    @Override
    public List<Post> getPostList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize() == null? 5 : postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }

    //TODO: 여기에 PostSearch 를 사용할 경우, 페이징도 동시에 할 수 있다.
    @Override
    public List<Post> searchPostByTitle(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.title.contains(postSearch.getKeyword() == null? "" : postSearch.getKeyword()))
                .limit(postSearch.getSize() == null? 5 : postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.createdDate.desc())
                .fetch();
    }

    @Override
    public List<Post> searchPostByContent(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.content.contains(postSearch.getKeyword() == null? "" : postSearch.getKeyword()))
                .limit(postSearch.getSize() == null? 5 : postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.createdDate.desc())
                .fetch();
    }
}
