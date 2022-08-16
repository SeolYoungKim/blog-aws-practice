package toyproject.blogawspractice.domain.user;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityFactory.class)
public @interface WithMockCustomUser {

    String username() default "kim";

    String userEmail() default "email@email.com";

    String userPicture() default "userPicture";


}
