package bitc.fullstack405.finalprojectspringboot.database.dto;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
  private String token;
  private int expiresIn;
  private UserEntity userEntity;
}
