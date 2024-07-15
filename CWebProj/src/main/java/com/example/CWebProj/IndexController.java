package com.example.CWebProj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
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
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	@GetMapping("/sidebar")
	public String sidebar() {
		return "sidebar_test";
	}
	@GetMapping("/board")
	public String board() {
		return "board_sample";
	}
	@GetMapping("/create")
	public String create() {
		return "create_form_sample";
	}
}
