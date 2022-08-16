package toyproject.blogawspractice.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import toyproject.blogawspractice.domain.user.Role;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/category/**", "/categories", "/write", "/api/**", "/post/**", "/posts").hasRole(Role.USER.name())  // Role.USER : Role 타입. 여기선 String Type을 넘겨줘야 하므로 name()사용.
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()  // userInfoEndpoint로 부터 최종 사용자의 사용자 속성을 가져옴
                .userService(customOAuth2UserService);  // 소셜 로그인 성공 -> 후속 조치를 진행할 UserService 인터페이스 구현체를 등록 (사용자 정보 처리)

        return http.build();
    }
}