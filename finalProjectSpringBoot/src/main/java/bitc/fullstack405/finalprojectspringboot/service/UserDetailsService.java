package bitc.fullstack405.finalprojectspringboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//implements org.springframework.security.core.userdetails.UserDetailsService // 클래스 활성화 시 해당 내용 삽입

@Service
@RequiredArgsConstructor
public class UserDetailsService  {

//  private final UserRepository userRepository;
//
//  @Override
//  public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
//    userEntity user = adminRepository.findByUserAccount(userAccount).orElseThrow(() -> newUsernameNotFoundException(userAccount + " 는 등록되지 않은 계정입니다."));
//
//    return admin;
//  }  // user 관련 엔티티, 레파지토리 구현 후 주석 해제 및 수정
}
