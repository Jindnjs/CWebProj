package com.example.CWebProj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

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
	@GetMapping("/sidd")
	public String sidd() {
		return "sidd";
	}
}
