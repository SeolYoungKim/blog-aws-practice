package toyproject.blogawspractice.repository.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        //given
        User user = User.builder()
                .username("name")
                .userRole(Role.USER)
                .userEmail("email@gmail.com")
                .userPicture("picture")
                .build();

        userRepository.save(user);

        //when
        User user1 = userRepository.getUserFromEmail("email@gmail.com").orElse(null);

        //then
        Assertions.assertThat(user.getId()).isEqualTo(user1.getId());
    }
}