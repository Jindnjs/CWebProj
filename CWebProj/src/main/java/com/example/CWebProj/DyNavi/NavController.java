package com.example.CWebProj.DyNavi;

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
	
	@GetMapping("/nav/test")
	public String readNav(Model model) {
		
		List<MenuCateg> menuCategories = navService.getAllMenuCategories();
        Map<Integer, List<MenuCateg>> groupedMenuCategories = menuCategories.stream()
                .collect(Collectors.groupingBy(MenuCateg::getMenuRate));
        model.addAttribute("groupedMenuCategories", groupedMenuCategories);

		return "/nav/nav";
	}
}
