package com.example.CWebProj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/")
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

	@GetMapping("/sidebar")
	public String sidebar() {
		return "sidebar_test";
	}
	@GetMapping("/list_album")
	public String list_album() {
		return "list_album";
	}
}
