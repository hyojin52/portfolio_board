package org.project.portfolio.configuration;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.configuration.filter.JwtTokenFilter;
import org.project.portfolio.exception.CustomAuthenticationEntryPoint;
import org.project.portfolio.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  
  private final AccountService accountService;
  
  @Value("${jwt.secret-key}")
  private String key;
  
  private static final String[] PERMIT_URL_ARRAY = {
          /* swagger v2 */
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**",
          /* swagger v3 */
          "/v3/api-docs/**",
          "/swagger-ui/**"
  };
  
  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/", "/api/v1/account/login", "/api/v1/account/join").permitAll()
                    .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                    .anyRequest().authenticated()
            )
            .logout(logout -> logout.permitAll())
            .addFilterBefore(new JwtTokenFilter(key, accountService), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            );
    
    return http.build();
  }
}

