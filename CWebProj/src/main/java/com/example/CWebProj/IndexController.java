package com.example.CWebProj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String test() {
		return "test";
	}
	
	@GetMapping("/main")
	public String index() {
		return "index";
	}
	@GetMapping("/index")
	public String index1() {
		return "index";
	}
	@GetMapping("/index.html")
	public String index2() {
		return "index";
	}
	@GetMapping("/nav_exam")
	   public String signup() {
	      return "nav_exam";
	}
}
