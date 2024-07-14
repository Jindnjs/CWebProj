package com.example.CWebProj.Nav;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

	@GetMapping("/test")
	public String navtest() {
		return "test";
	}
	@GetMapping("/nav/test")
	public String index() {
		return "nav/nav";
	}
}
