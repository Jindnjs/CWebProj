package com.example.CWebProj.Nav;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NavController {

	private final NavService navService;
	
	@GetMapping("/test")
	public String navtest() {
		return "test";
	}
	@GetMapping("/nav/test")
	public String readNav(Model model) {
		
		List<MenuCateg> menuCategories = navService.getAllMenuCategories();
		System.out.println(menuCategories.get(0).getMenuName());
        // 메뉴 이름을 기준으로 그룹화하여 Map으로 변환
        Map<Integer, List<MenuCateg>> groupedMenuCategories = menuCategories.stream()
                .collect(Collectors.groupingBy(MenuCateg::getMenuRate));

        model.addAttribute("groupedMenuCategories", groupedMenuCategories);

		return "/nav/nav";
	}
}
