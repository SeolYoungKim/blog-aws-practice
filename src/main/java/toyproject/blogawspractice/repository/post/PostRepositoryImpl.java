package toyproject.blogawspractice.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.web.request.post.PostSearch;

import java.util.List;

import static toyproject.blogawspractice.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findByTitle(String title) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.title.eq(title))
                .fetchOne();
    }

    @Override
    public Post findByContent(String content) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.content.eq(content))
                .fetchOne();
    }

    @Override
    public Post findByUser(User user) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.user.eq(user))
                .fetchOne();
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
