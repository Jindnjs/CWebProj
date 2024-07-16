package com.example.CWebProj;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "test";
	}
	@GetMapping("/sidd")
	public String sidd() {
		return "/readform/textform";
	}
	@GetMapping("/cccc")
	public String cccc() {
		return "/createform/lay_create_form";
	}
}
