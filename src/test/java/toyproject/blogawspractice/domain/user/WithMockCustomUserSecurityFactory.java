package toyproject.blogawspractice.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import toyproject.blogawspractice.config.auth.dto.SessionUser;
import toyproject.blogawspractice.repository.user.UserRepository;

public class WithMockCustomUserSecurityFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

//        CustomUserDetails principal = new (customUser.username(), customUser.userEmail(), customUser.userPicture(), customUser.userRole());

        User user = User.builder()
                .username(customUser.username())
                .userEmail(customUser.userEmail())
                .userPicture(customUser.userPicture())
                .userRole(Role.USER)
                .build();
        userRepository.save(user);

        SessionUser sessionUser = new SessionUser(user);

        //TODO: 내 코드에서 UserDetails를 가져올 수 있는 방법은?

        Authentication auth = new UsernamePasswordAuthenticationToken(sessionUser, null, AuthorityUtils.createAuthorityList(Role.USER.name()));

        context.setAuthentication(auth);

        return context;
    }
}
