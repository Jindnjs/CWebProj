package com.example.CWebProj.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.CWebProj.AwsBucket.S3Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CUserService implements UserDetailsService {

	@Autowired
	private CUserRepository cuserRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private JavaMailSender mailsender;


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
	
	//유저 데이터 저장
	public void userdata(String username) {
        CUser cuser = new CUser();
        cuser.setUsername(username);
        this.cuserRepository.save(cuser);
    }
	
	//유저 데이터 다 불러오기
	public List<CUser> readlist() {
		return cuserRepository.findAll();
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
	

	//비번 리셋
	
	
	//비번 자동생성
	private boolean isStrongPassword(String password) {
		String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		return password.matches(pattern);
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
