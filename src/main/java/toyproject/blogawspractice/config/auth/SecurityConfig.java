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
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                        .antMatchers("/category/add", "/category/**/edit", "/category/**/delete").hasRole(Role.ADMIN.name())
                        .antMatchers("/categories", "/category/**", "/write", "/api/**", "/post/**", "/posts", "/setting").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint()  // userInfoEndpoint로 부터 최종 사용자의 사용자 속성을 가져옴
                        .userService(customOAuth2UserService)); // 소셜 로그인 성공 -> 후속 조치를 진행할 UserService 인터페이스 구현체를 등록 (사용자 정보 처리)
                                                                // UserEndpoint에 대한 위임 전략임

        return http.build();
    }
}
