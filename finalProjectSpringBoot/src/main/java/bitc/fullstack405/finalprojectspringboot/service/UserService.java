package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.LoginDto;
import bitc.fullstack405.finalprojectspringboot.database.dto.LoginResponseDto;
import bitc.fullstack405.finalprojectspringboot.database.dto.ResponseDto;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public void saveUser(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

  public ResponseDto<LoginResponseDto> login(LoginDto loginDto) {
    String userAccount = loginDto.getUserAccount();
    String userPw = loginDto.getUserPw();
    String pwEncode = bCryptPasswordEncoder.encode(userPw);

    try {
      boolean existed = userRepository.existsByUserAccountAndUserPw(userAccount, pwEncode);
      if(!existed) {
        return ResponseDto.setFailed("입력하신 로그인 정보가 존재하지 않습니다.");
      }
    }
    catch (Exception e) {
      return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
    }

    UserEntity userEntity = null;

    try {
      userEntity = userRepository.findByUserAccount(userAccount).get();
    } catch (Exception e) {
      return ResponseDto.setFailed("데이터베이스 연결에 실패하였습니다.");
    }

    userEntity.setUserPw("");

    String token = "";
    int exprTime = 3600000;     // 1h

    LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, userEntity);

    return ResponseDto.setSuccessData("로그인에 성공하였습니다.", loginResponseDto);
  }
}
