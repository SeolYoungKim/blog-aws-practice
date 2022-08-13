package toyproject.blogawspractice.repository.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.blogawspractice.domain.category.Category;

import static toyproject.blogawspractice.domain.category.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Category findByName(String name) {
        return jpaQueryFactory.selectFrom(category)
                .where(category.name.eq(name))
                .fetchOne();
    }
}
