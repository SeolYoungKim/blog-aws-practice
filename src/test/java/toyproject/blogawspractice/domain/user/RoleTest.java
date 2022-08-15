package toyproject.blogawspractice.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void user_name() {
        String name = Role.USER.name();
        Role user = Role.USER;

        System.out.println(name);
        System.out.println(user);
    }
}