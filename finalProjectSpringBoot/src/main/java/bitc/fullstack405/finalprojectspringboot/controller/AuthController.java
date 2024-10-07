package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.dto.ResponseDto;
import bitc.fullstack405.finalprojectspringboot.database.dto.SignUpDto;
import bitc.fullstack405.finalprojectspringboot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 예제용

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  final AuthService authService;

  @PostMapping("/signUp")
  public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
    return authService.signUp(requestBody);
  }
}
