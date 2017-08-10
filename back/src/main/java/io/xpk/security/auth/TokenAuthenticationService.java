package io.xpk.security.auth;

import static java.util.Collections.emptyList;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TokenAuthenticationService {
  private static final long EXPIRATIONTIME = 864_000_000; // 10 days
  private static final String SECRET = "ThisIsASecret";
  private static final String BEARER = "Bearer";
  private static final String AUTHORIZATION = "Authorization";

  public void addAuthentication(HttpServletResponse res, String username) {
    String JWT = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    res.addHeader(AUTHORIZATION, BEARER + " " + JWT);
  }

  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION);
    if (token != null) {
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token.replace(BEARER, ""))
          .getBody()
          .getSubject();

      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
          null;
    }
    return null;
  }
}