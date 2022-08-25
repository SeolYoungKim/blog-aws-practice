package toyproject.blogawspractice.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;
import java.util.Optional;

import static toyproject.blogawspractice.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // TODO: title, content, user는 검색 로직에 활용될 수 있도록 "일치" -> "포함"으로 변경 예정
    @Override
    public Optional<Post> findByTitle(String title) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(post)
                .where(post.title.eq(title))
                .fetchOne());
    }

    @Override
    public Optional<Post> findByContent(String content) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(post)
                .where(post.content.eq(content))
                .fetchOne());
    }

    @Override
    public Optional<Post> findByUser(User user) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(post)
                .where(post.user.eq(user))
                .fetchOne());
    }

    @Override
    public List<Post> getPostList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
