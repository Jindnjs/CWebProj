package com.example.CWebProj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.CWebProj.banner.BannerService;

@Controller
public class IndexController {
	
	@Autowired
	BannerService bannerService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;

	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("downpath", "https://" + downpath);
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
