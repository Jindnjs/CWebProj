package com.example.CWebProj.Autho;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthoController {
	@GetMapping("/autho")
	public String index() {
		
		return "autho/check";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		
		return "autho/admin";
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
	@GetMapping("/manager")
	public String manager() {
		
		return "autho/manager";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user")
	public String user() {
		
		return "autho/user";
	}
}