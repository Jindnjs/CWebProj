package com.example.CWebProj.User;

import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class CUserController {

	@Autowired
	private CUserService cuserService;
	
	@GetMapping("/signup")
	public String signup() {
		return "user/signup";
	}

	@PostMapping("/signup")
	public String signup(CUser cuser) {
		cuserService.create(cuser);
		return "user/signup";
		
	}

	@GetMapping("/signin")
	public String signin() {
		return "user/signin";
	}

	@GetMapping("/signout")
	public String signout() {
		return "user/signin";
	}

	@GetMapping("/profile")
	public String profile() {
		return "user/profile";
	}

	@GetMapping("/findid")
	public String findid() {
		return "user/findid";
	}

	@GetMapping("/findpw")
	public String findpw() {
		return "user/findpw";
	}

	@PostMapping("/findpw")
	public String findpw(Model model, @RequestParam("username") String username,
	                     @Valid CUserForm cuserForm, BindingResult bindingResult, Principal principal) {
	    CUser cuser = this.cuserService.findpw(username);

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("cuser", cuser);
	        return "user/resetpw";
	    }
	    return "redirect:/signin"; 
	}
	
	@GetMapping("/resetpw")
	public String resetpw() {
		return "user/resetpw";
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
