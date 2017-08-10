package io.xpk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xpk.security.auth.JWTAuthenticationFilter;
import io.xpk.security.auth.JWTLoginFilter;
import io.xpk.security.auth.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final ObjectMapper objectMapper;
  private final TokenAuthenticationService tokenAuthenticationService;

  public WebSecurityConfig(ObjectMapper objectMapper, TokenAuthenticationService tokenAuthenticationService) {
    this.objectMapper = objectMapper;
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        // We filter the api/login requests
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), objectMapper, tokenAuthenticationService),
            UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),
            UsernamePasswordAuthenticationFilter.class);
  }

  // TODO: remove this when we actually have users
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser("steve").password("password").roles("USER").and()
        .withUser("ryan").password("password").roles("USER").and()
        .withUser("steve1").password("password").roles("USER").and()
        .withUser("ryan1").password("password").roles("USER");
  }

}