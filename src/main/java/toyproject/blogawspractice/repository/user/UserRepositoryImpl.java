package toyproject.blogawspractice.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import toyproject.blogawspractice.domain.user.User;

import java.util.Optional;

import static toyproject.blogawspractice.domain.user.QUser.user;
import static toyproject.blogawspractice.repository.user.logic.FindEmailByOAuth2User.findEmail;

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

    @Override
    public Optional<User> getUserFromOAuth2User(OAuth2User oAuth2User) {
        String email = findEmail(oAuth2User);

        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(user)
                        .where(user.userEmail.eq(email))
                        .fetchOne());
    }

}
