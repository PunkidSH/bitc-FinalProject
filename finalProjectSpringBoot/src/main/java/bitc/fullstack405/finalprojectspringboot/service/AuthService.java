package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.ResponseDto;
import bitc.fullstack405.finalprojectspringboot.database.dto.SignUpDto;
import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// 예제용

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public ResponseDto<?> signUp(SignUpDto signUpDto) {
    String userAccount = signUpDto.getUserAccount();
    String userPw = signUpDto.getUserPw();

    try {
      if(userRepository.existsByUserAccount(userAccount)) {
        return ResponseDto.setFailed("중복된 계정명 입니다.");
      }
    } catch (Exception e) {
      return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
    }

    Role role = Role.ROLE_PRESIDENT;

    String pwEncode = bCryptPasswordEncoder.encode(userPw);

    UserEntity userEntity = UserEntity.builder()
        .userAccount(userAccount)
        .userPhone(signUpDto.getUserPhone())
        .role(signUpDto.getUserPermission())
        .userPw(pwEncode)
        .name(signUpDto.getUserName())
        .build();

    try {
      userRepository.save(userEntity);
    }
    catch (Exception e) {
      return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
    }

    return ResponseDto.setSuccess("회원 생성에 성공했습니다.");
  }

}
