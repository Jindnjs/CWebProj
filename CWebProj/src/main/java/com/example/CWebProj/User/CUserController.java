package com.example.CWebProj.User;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CWebProj.Autho.AuthenKeyValService;
import com.example.CWebProj.Mail.SendMailService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CUserController {

	private final CUserService cuserService;
	
	private final SendMailService mailService;
	
	private final AuthenKeyValService authenKeyValService;
	
	@GetMapping("/signup")
	public String signup() {
		return "authentication/signup";
	}

	@PostMapping("/signup")
	public String signup(CUser cuser) {
		cuserService.create(cuser);
		return "redirect:/signin";
		
	}

	@GetMapping("/signin")
	public String signin() {
		return "authentication/signin";
	}

	@GetMapping("/profile")
	public String profile() {
		return "user/profile";
	}

	@GetMapping("/findid")
	public String findid() {
		return "user/findid";
	}

	@GetMapping("/resetpw")
	public String findpw() {
		return "authentication/resetpw";
	}

//	@PostMapping("/findpw")
//	public String findpw(Model model, @RequestParam("username") String username,
//	                     @Valid CUserForm cuserForm, BindingResult bindingResult, Principal principal) {
//	    CUser cuser = this.cuserService.findpw(username);
//
//	    if (bindingResult.hasErrors()) {
//	        model.addAttribute("cuser", cuser);
//	        return "user/resetpw";
//	    }
//	    return "redirect:/signin"; 
//	}
	//UUID 생성 및 이메일 전송
	@PostMapping("/findpw")
	public String sendResetPassword(
		@RequestParam("email") String email) throws MessagingException {		
		System.out.println("서버로 넘어온 이메일 = " + email);
		
		//이메일 주소의 유효성 검증
		//만약 없으면 회원가입창으로 보냄
		
		mailService.sendResetPasswordEmail(email);
		return "redirect:/signin";
	}
	@GetMapping("/reset/{uuid}")
	public String resetPassword(@PathVariable("uuid") String uuid) {
		String email = authenKeyValService.getValue(uuid);
		if (email == null) {
			System.out.println("redis에 이메일이 없습니다.");
			return "redirect:/signin";
		}
		return "authentication/resetpw";
	}
	@PostMapping("/reset/{uuid}")
	public String resetPassword(@PathVariable("uuid") String uuid, 
			@RequestParam("password") String password) {
		cuserService.resetPassword(uuid, password);
		return "redirect:/signin";
	}

	
	
	@GetMapping("/findpw")
	public String resetpw() {
		return "authentication/findpw";
	}
	
	//구글 로그인
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googleClientId;

	@GetMapping("/glogin")
	public ResponseEntity<?> getGoogleAuthUrl(HttpServletRequest request) throws Exception {

		String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
				+ "&redirect_uri=http://localhost:8080/login/oauth2/code/google&response_type=code&scope=email%20profile%20openid&access_type=offline";

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(reqUrl));

		
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

}
