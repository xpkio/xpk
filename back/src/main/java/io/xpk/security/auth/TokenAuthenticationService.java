package io.xpk.security.auth;

import static java.util.Collections.emptyList;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

class TokenAuthenticationService {
  static final long EXPIRATIONTIME = 864_000_000; // 10 days
  static final String SECRET = "ThisIsASecret";
  static final String BEARER = "Bearer";
  static final String AUTHORIZATION = "Authorization";

  static void addAuthentication(HttpServletResponse res, String username) {
    String JWT = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    res.addHeader(AUTHORIZATION, BEARER + " " + JWT);
    try {
      res.getWriter().write(String.format("{\n" +
          "  \"username\" : \"%s\"\n" +
          "}", username));
    } catch (IOException e) {
      e.printStackTrace();
    }
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