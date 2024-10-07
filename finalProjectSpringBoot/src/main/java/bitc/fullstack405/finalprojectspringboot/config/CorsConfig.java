package bitc.fullstack405.finalprojectspringboot.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CorsConfig {

  public static CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration =  new CorsConfiguration();

    ArrayList<String> allowedOriginPatterns = new ArrayList<>();

    allowedOriginPatterns.add("http://localhost:5173");
    configuration.setAllowedOrigins(allowedOriginPatterns);

    ArrayList<String> allowedHttpMethods = new ArrayList<>();

    allowedHttpMethods.add("GET");
    allowedHttpMethods.add("POST");
    allowedHttpMethods.add("PUT");
    allowedHttpMethods.add("DELETE");
    configuration.setAllowedMethods(allowedHttpMethods);

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}