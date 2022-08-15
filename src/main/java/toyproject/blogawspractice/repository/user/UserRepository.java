package toyproject.blogawspractice.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.blogawspractice.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{
}
