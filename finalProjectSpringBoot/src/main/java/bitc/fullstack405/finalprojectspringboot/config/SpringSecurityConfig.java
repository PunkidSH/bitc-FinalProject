package bitc.fullstack405.finalprojectspringboot.config;

import lombok.RequiredArgsConstructor;
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

// 권한 : 협회장, 총무, 회원, 게스트(권한없음)

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return webSecurity -> webSecurity.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/static/**"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> {})
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/temp")
            .permitAll()
        )
        .formLogin(login -> login
//            .loginPage("/login")
            .failureUrl("/login?error")
            .defaultSuccessUrl("/")
            .usernameParameter("userAccount")
            .passwordParameter("userPw")
            .permitAll()
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
//    authProvider.setUserDetailsService(userDetailsService); // service 구현 후 주석 해제
    authProvider.setPasswordEncoder(passwordEncoder());

    return new ProviderManager(authProvider);
  }
}
