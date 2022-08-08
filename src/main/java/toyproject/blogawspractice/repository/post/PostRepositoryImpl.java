package toyproject.blogawspractice.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.blogawspractice.domain.post.Post;
import toyproject.blogawspractice.domain.post.QPost;

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
    public Post findByAuthor(String author) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.author.eq(author))
                .fetchOne();
    }
}
