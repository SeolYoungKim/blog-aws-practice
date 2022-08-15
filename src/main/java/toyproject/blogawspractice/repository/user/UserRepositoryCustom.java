package toyproject.blogawspractice.repository.user;

import toyproject.blogawspractice.domain.user.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByEmail(String email);
}
