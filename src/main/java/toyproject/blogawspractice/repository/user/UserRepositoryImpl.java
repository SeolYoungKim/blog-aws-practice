package toyproject.blogawspractice.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import toyproject.blogawspractice.domain.user.User;

import java.util.Optional;

import static toyproject.blogawspractice.domain.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> getUserFromEmail(String email) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                .where(user.userEmail.eq(email))
                .fetchOne());
    }
}
