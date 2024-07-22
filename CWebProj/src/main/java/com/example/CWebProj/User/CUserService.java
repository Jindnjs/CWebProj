package com.example.CWebProj.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.CWebProj.Autho.AuthenKeyValService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CUserService implements UserDetailsService {

	private final CUserRepository cuserRepository;
	
	private final AuthenKeyValService authenKeyValService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	// 로그인처리
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		CUser cuser = cuserRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        });
		return new PrincipalDetails(cuser);
	}

	
	// 회원가입처리
	public void create(CUser cuser) {

		cuser.setEnabled(true);
		cuser.setRole("ROLE_USER");
		cuser.setCdate(LocalDateTime.now());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		cuser.setPassword(passwordEncoder.encode(cuser.getPassword()));
		cuserRepository.save(cuser);
	}
	
	
	
	//비번 재설정용
	public void resetPassword(String uuid, String newPassword) {
		//redis에 uuid가 있는지 확인, 없으면 error
		String email = authenKeyValService.getValue(uuid);
		if (email == null) {
			System.out.println("redis에 이메일이 없습니다.");
			return;
		}

		//redis에서 uuid로 email을 찾아온다.
		CUser cuser = cuserRepository.findByUsername(email).get();

		//비밀번호 재설정
		cuser.setPassword(passwordEncoder.encode((newPassword)));

		//비밀번호 업데이트 후 redis에서 uuid를 지운다.
		authenKeyValService.delete(uuid);
	}
	
	
	
	public boolean checkEmail(String Email) {
		Optional<CUser> oc = cuserRepository.findByUsername(Email);
		if(oc.isPresent())
			return true;
		else
			return false;
		
	}
	
	
	//유저 데이터 다 불러오기
	public List<CUser> readlist() {
		return cuserRepository.findAll();
	}
	
	//ADMIN만 빼고 불러오기
	public List<CUser> getAllNonAdminUsers() {
        return cuserRepository.findByRoleNot("ROLE_ADMIN");
    }
	
	// 유저 상세정보 가져오기
	public CUser readdetail(Integer cid) {
		Optional<CUser> oc = cuserRepository.findById(cid);
		return oc.get();
	}
		
	
	//유저 정보 업데이트
	public void update(CUser cuser) {
		cuserRepository.save(cuser);
	}
	
	
	
	//비번 잊었을때
	public CUser findpw(String username) {

		Optional<CUser> cuser = this.cuserRepository.findByUsername(username);

		if (cuser.isPresent()) {
			return cuser.get();
		}else {
			throw new DataNotFoundException("존재하지 않는 이메일입니다.");
		}
	}
	
	
	public CUser authen() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String username = userDetails.getUsername();
	        return this.cuserRepository.findByUsername(username).orElse(null);
		}
		
		return null;
	}

}
