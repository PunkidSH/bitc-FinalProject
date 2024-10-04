package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import bitc.fullstack405.finalprojectspringboot.database.entity.TempEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.service.TempService;
import bitc.fullstack405.finalprojectspringboot.service.UserDetailsService;
import bitc.fullstack405.finalprojectspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("http://localhost:5173")
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;

  @GetMapping("/login")
  public String login() {
    return "/login";
  }

  @PostMapping("/signup")
  public String signup(@RequestParam String userAccount, @RequestParam String userPw, @RequestParam String name, @RequestParam String userPhone) {

    Role role = Role.ROLE_PRESIDENT;

    String pwEncode = bCryptPasswordEncoder.encode(userPw);

    UserEntity userEntity = UserEntity.builder()
        .userAccount(userAccount)
        .userPhone(userPhone)
        .role(role)
        .userPw(pwEncode)
        .name(name)
        .build();

    userService.saveUser(userEntity);

    return "redirect:/login";
  }
}
