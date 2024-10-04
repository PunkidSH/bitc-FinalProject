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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// 권한 : 협회장, 총무, 회원, 게스트(권한없음)

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

  final UserDetailsService userDetailsService;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return webSecurity -> webSecurity.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/static/**"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/user/login", "/user/signup")
            .permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginPage("/user/login")
            .failureUrl("/user/login")
            .defaultSuccessUrl("http://localhost:5173/loginsuccess")
            .usernameParameter("userAccount")
            .passwordParameter("userPw")
            .permitAll()
        )
        .sessionManagement(auth -> auth
            .maximumSessions(1)
            .maxSessionsPreventsLogin(true)
        )
        .logout(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService); // service 구현 후 주석 해제
    authProvider.setPasswordEncoder(passwordEncoder());

    return new ProviderManager(authProvider);
  }
}
