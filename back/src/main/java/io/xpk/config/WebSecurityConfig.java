package io.xpk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xpk.domain.repo.XpkUserRepository;
import io.xpk.security.auth.JWTAuthenticationFilter;
import io.xpk.security.auth.JWTLoginFilter;
import io.xpk.security.auth.TokenAuthenticationService;
import io.xpk.service.XpkUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final ObjectMapper objectMapper;
  private final TokenAuthenticationService tokenAuthenticationService;
  private final XpkUserRepository xpkUserRepository;

  public WebSecurityConfig(ObjectMapper objectMapper, TokenAuthenticationService tokenAuthenticationService, XpkUserRepository xpkUserRepository, XpkUserDetailsService xpkUserDetailsService) {
    this.objectMapper = objectMapper;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.xpkUserRepository = xpkUserRepository;
    this.xpkUserDetailsService = xpkUserDetailsService;
  }

  private final XpkUserDetailsService xpkUserDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(xpkUserDetailsService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .antMatchers(HttpMethod.POST, "/user").permitAll()
        .anyRequest().authenticated()
        .and()
        // We filter the api/login requests
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), xpkUserRepository, objectMapper, tokenAuthenticationService),
            UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),
            UsernamePasswordAuthenticationFilter.class);
  }
}
