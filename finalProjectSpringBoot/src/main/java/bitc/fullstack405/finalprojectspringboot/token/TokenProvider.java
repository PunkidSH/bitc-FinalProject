package bitc.fullstack405.finalprojectspringboot.token;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {

  private final String JWT_SECRET = "secret";
  private final long JWT_EXPIRATION_MS = 1000 * 60 * 60; // 1시간

  public String createToken(UserEntity userEntity) {
    Claims claims = (Claims) Jwts.claims();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

    claims.put("userAccount", userEntity.getUserAccount());
    claims.put("expiryDate", expiryDate);
    claims.put("issuedAt", now);
    claims.put("expiration", expiryDate);
    claims.put("role", userEntity.getRole());

    return Jwts.builder().claims(claims).compact();
  }

//  public boolean validateToken(String token) {
//
//  }
}
