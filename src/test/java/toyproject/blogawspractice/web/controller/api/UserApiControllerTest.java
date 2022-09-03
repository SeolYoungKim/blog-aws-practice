package toyproject.blogawspractice.web.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import toyproject.blogawspractice.domain.user.Role;
import toyproject.blogawspractice.domain.user.User;
import toyproject.blogawspractice.repository.user.UserRepository;
import toyproject.blogawspractice.web.request.user.RequestEditUser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("User의 역할을 수정할 수 있다.")
    @Test
    void roleUpdate() throws Exception {
        User user = User.builder()
                .userRole(Role.ADMIN)
                .userPicture("picture")
                .userEmail("admin-email")
                .username("kim")
                .build();

        userRepository.save(user);


        List<User> userList = IntStream.range(1, 4)
                .mapToObj(i -> User.builder()
                        .userPicture("pic")
                        .userRole(Role.USER)
                        .userEmail("email" + i)
                        .username("name" + i)
                        .build())
                .collect(Collectors.toList());

        userRepository.saveAll(userList);

        List<RequestEditUser> requestEditUsers = IntStream.range(1, 4)
                .mapToObj(i -> RequestEditUser.builder()
                        .userRole("관리자")
                        .userEmail("email" + i)
                        .build())
                .collect(Collectors.toList());


        mockMvc.perform(MockMvcRequestBuilders.patch("/user/edit")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestEditUsers))
                        .with(oauth2Login().attributes(attrs -> attrs.put("email", "admin-email"))))
                .andExpect(status().isOk())
                .andDo(print());


        assertThat(userRepository.getUserFromEmail("email1").get().getUserRole()).isEqualTo(Role.ADMIN);
        assertThat(userRepository.getUserFromEmail("email2").get().getUserRole()).isEqualTo(Role.ADMIN);
        assertThat(userRepository.getUserFromEmail("email3").get().getUserRole()).isEqualTo(Role.ADMIN);
    }
}