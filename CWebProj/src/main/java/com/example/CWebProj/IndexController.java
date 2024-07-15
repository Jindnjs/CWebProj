package com.example.CWebProj;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.CWebProj.Banner.BannerService;
import com.example.CWebProj.Nav.MenuCateg;
import com.example.CWebProj.Nav.NavService;

@Controller
public class IndexController {
	
	@Autowired
	BannerService bannerService;
	
	@Autowired
	NavService navService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("downpath", "https://" + downpath);
        List<MenuCateg> menuCategories = navService.getAllMenuCategories();
        Map<Integer, List<MenuCateg>> groupedMenuCategories = menuCategories.stream()
                .collect(Collectors.groupingBy(MenuCateg::getMenuRate));
        model.addAttribute("groupedMenuCategories", groupedMenuCategories);
		return "index";
	}
	
	@GetMapping("/index")
	public String index1(Model model) {
		model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("downpath", "https://" + downpath);
		return "index";
	}
	@GetMapping("/index.html")
	public String index2(Model model) {
		model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("downpath", "https://" + downpath);
		return "index";
	}
}
