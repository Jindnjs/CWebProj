package com.example.CWebProj.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

}
