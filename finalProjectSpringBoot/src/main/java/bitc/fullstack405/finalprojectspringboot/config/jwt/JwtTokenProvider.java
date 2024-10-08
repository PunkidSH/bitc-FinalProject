package bitc.fullstack405.finalprojectspringboot.config.jwt;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;

  public String generateToken(UserEntity userEntity, Duration expiredAt) {
    Date now = new Date();

    return makeToken(new Date(now.getTime() + expiredAt.toMillis()), userEntity);
  }

  private String makeToken(Date expiry, UserEntity userEntity) {
    Date now = new Date();

    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userEntity.getUserId());
    claims.put("userAccount", userEntity.getUserAccount());
    claims.put("userName", userEntity.getUsername());
    claims.put("userDepart", userEntity.getUserDepart());
    claims.put("userPhone", userEntity.getUserPhone());
    claims.put("role", userEntity.getRole());


  }
}
