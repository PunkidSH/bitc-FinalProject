package bitc.fullstack405.finalprojectspringboot.config;

import bitc.fullstack405.finalprojectspringboot.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// 권한 : 협회장, 총무, 정회원, 준회원(승인대기), 게스트(로그인 x, 권한없음)

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

  private final UserDetailsService userDetailsService;
  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return webSecurity -> webSecurity.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/static/**"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
//        .cors(cors -> cors.configurationSource(CorsConfig.corsConfigurationSource())) // 안먹는중
        .cors(cors -> cors.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/user/login", "/user/signup", "/api/auth/signUp")
            .permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .usernameParameter("userAccount")
            .passwordParameter("userPw")
        )
        .exceptionHandling(except -> except
              .authenticationEntryPoint(customAuthenticationEntryPoint) // 커스텀 로그인 페이지
        )
        .sessionManagement(manage -> manage
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 토큰 방식이라 세션 생성 ㄴㄴ
        )
        .logout(Customizer.withDefaults());
//        .addFilter(new Jwt);
//        .addFilterBefore();

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authProvider);
  }
}
