package com.example.CWebProj.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.CWebProj.AwsBucket.S3Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CUserService implements UserDetailsService {

	private final CUserRepository cuserRepository;
	
	private final S3Service s3Service;
	
	private final PasswordEncoder passwordEncoder;
	// 로그인처리
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<CUser> tcuser = cuserRepository.findByUsername(username);
		if (tcuser.isEmpty()) {
			throw new UsernameNotFoundException("회원가입 되어 있지 않습니다.");
		}
		CUser cuser = tcuser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("ROLE_USER".equals(cuser.getRole())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else if ("ROLE_MANAGER".equals(cuser.getRole())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return new User(cuser.getUsername(), cuser.getPassword(), authorities);

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
	
//	//비번 재설정용
//	@Transactional
//	public void resetPassword(String uuid, String newPassword) {
//		//redis에 uuid가 있는지 확인, 없으면 error
//		if (email == null) {
//			System.out.println("redis에 이메일이 없습니다.");
//			return;
//		}
//
//		//redis에서 uuid로 email을 찾아온다.
//		CUser cuser = cuserRepository.findByUsername(email).get();
//
//		//비밀번호 재설정
//		cuser.setPassword(passwordEncoder.encode((newPassword)));
//
//		//비밀번호 업데이트 후 redis에서 uuid를 지운다.
//		redisService.deleteValues(uuid);
//	}
	
	
	//유저 데이터 저장
	public void userdata(String username) {
        CUser cuser = new CUser();
        cuser.setUsername(username);
        this.cuserRepository.save(cuser);
    }
	
	public boolean checkEmail(String Email) {
		Optional<CUser> oc = cuserRepository.findByUsername(Email);
		if(oc.isPresent())
			return true;
		else
			return false;
		
	}
	
	// 구글로그인처리
	@Autowired
	private HttpServletRequest req;

	public int logincheck(String username) {

		Optional<CUser> tcuser = cuserRepository.findByUsername(username);
		CUser cuser = tcuser.get();

		if (cuser != null) {

			List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			list.add(new SimpleGrantedAuthority("ROLE_USER"));

			SecurityContext sc = SecurityContextHolder.getContext();

			sc.setAuthentication(new UsernamePasswordAuthenticationToken(cuser.getUsername(), null, list));

			HttpSession session = req.getSession(true);

			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

			return 1;

		} else {

			return 0;
		}

	}

}
