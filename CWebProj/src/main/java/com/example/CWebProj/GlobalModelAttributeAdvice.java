package com.example.CWebProj;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.CWebProj.Banner.BannerService;
import com.example.CWebProj.Nav.MenuCateg;
import com.example.CWebProj.Nav.NavService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalModelAttributeAdvice {


	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
	private final BannerService bannerService;
    private final NavService navService;
    
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
    	model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("downpath", "https://" + downpath);
        List<MenuCateg> menuCategories = navService.getAllMenuCategories();
        Map<Integer, List<MenuCateg>> groupedMenuCategories = menuCategories.stream()
                .collect(Collectors.groupingBy(MenuCateg::getMenuRate));
        model.addAttribute("groupedMenuCategories", groupedMenuCategories);
    }
}
