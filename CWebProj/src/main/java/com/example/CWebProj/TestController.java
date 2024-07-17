package com.example.CWebProj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.CWebProj.DyNavi.NavService;

@Controller
public class TestController {

	@Autowired
	private NavService navService;
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	@GetMapping("/sidd/9")
	public String sidd(Model model) {
		model.addAttribute("MenuCate", navService.getMenu(9));
		model.addAttribute("sidebar", navService.getSidebar(9));
		return "readform/textform";
	}
	@GetMapping("/cccc/9")
	public String cccc(Model model) {
		model.addAttribute("MenuCate", navService.getMenu(9));
		model.addAttribute("sidebar", navService.getSidebar(9));
		return "createform/lay_create_form";
	}
}
