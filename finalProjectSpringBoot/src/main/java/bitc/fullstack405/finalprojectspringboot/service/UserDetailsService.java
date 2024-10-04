package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// // 클래스 활성화 시 해당 내용 삽입

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUserAccount(userAccount).orElseThrow(() -> new UsernameNotFoundException(userAccount + " 는 등록되지 않은 계정입니다."));

    return user;
  }
}
