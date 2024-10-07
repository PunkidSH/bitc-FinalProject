package bitc.fullstack405.finalprojectspringboot.database.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

  @NotBlank
  private String userAccount;

  @NotBlank
  private String userPw;
}
