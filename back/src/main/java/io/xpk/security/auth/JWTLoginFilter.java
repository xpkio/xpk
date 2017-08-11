package io.xpk.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xpk.domain.obj.XpkUser;
import io.xpk.domain.repo.XpkUserRepository;
import io.xpk.web.obj.AccountCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

  private final XpkUserRepository xpkUserRepository;
  private final ObjectMapper objectMapper;
  private final TokenAuthenticationService tokenAuthenticationService;

  public JWTLoginFilter(String url, AuthenticationManager authManager, XpkUserRepository xpkUserRepository, ObjectMapper objectMapper, TokenAuthenticationService tokenAuthenticationService) {
    super(new AntPathRequestMatcher(url));
    this.xpkUserRepository = xpkUserRepository;
    this.objectMapper = objectMapper;
    this.tokenAuthenticationService = tokenAuthenticationService;
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {

    AccountCredentials accountCredentials = objectMapper.readValue(req.getInputStream(), AccountCredentials.class);

    XpkUser xpkUser = xpkUserRepository.findByUsername(accountCredentials.getUsername());
    if (xpkUser == null) {
      throw new UsernameNotFoundException(accountCredentials.getUsername());
    }
    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            accountCredentials.getUsername(),
            accountCredentials.getPassword() + xpkUser.getSalt(),
            Collections.emptyList()
        )
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    tokenAuthenticationService.addAuthentication(res, auth.getName());
  }
}
