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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CUserService implements UserDetailsService {

	@Autowired
	private CUserRepository cuserRepository;

	// 로그인처리
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<CUser> tcuser = cuserRepository.findByusername(username);
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
	
	//이메일 체크
	public boolean userEmailCheck(String cemail, String username) {

        CUser user = cuserRepository.findByCemail(cemail);
        if(user!=null && user.getUsername().equals(username)) {
            return true;
        }
        else {
            return false;
        }
    }
	
	
	
	//구글로그인처리
	@Autowired
	private HttpServletRequest req;
	
	public int logincheck(String username) {
		
		Optional<CUser> tcuser = cuserRepository.findByusername(username);
		CUser cuser = tcuser.get();
	
		if (cuser != null) {
			
			List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			SecurityContext sc = SecurityContextHolder.getContext();
			
			sc.setAuthentication(new UsernamePasswordAuthenticationToken(cuser.getUsername(), null, list));
			
			
			HttpSession session = req.getSession(true);
			
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,sc);
		
			return 1;
			
		} else {
			
			return 0;
		}
	
	
	}

}
