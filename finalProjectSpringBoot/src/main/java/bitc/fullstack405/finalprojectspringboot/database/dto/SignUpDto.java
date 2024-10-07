package bitc.fullstack405.finalprojectspringboot.database.dto;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
  private String userAccount;
  private String userPw;
  private String userName;
  private String userPhone;
  private String userDepart;
  private Role userPermission;
}
