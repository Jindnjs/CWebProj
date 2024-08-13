package com.example.CWebProj;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.CWebProj.AutoList.AutoListService;
import com.example.CWebProj.DyNavi.MenuCateg;
import com.example.CWebProj.DyNavi.NavService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalModelAttributeAdvice {


	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
    private final NavService navService;
    private final AutoListService autoListService;
    
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
    	
    	//버킷링크
        model.addAttribute("downpath", "https://" + downpath);
        
        //네비게이션 메뉴
        List<MenuCateg> menuCategories = navService.getAllMenuCategories();
        Map<Integer, List<MenuCateg>> groupedMenuCategories = menuCategories.stream()
                .collect(Collectors.groupingBy(MenuCateg::getMenuRate));
        model.addAttribute("groupedMenuCategories", groupedMenuCategories);
        
        //헤더 권한 관련
        String bannerRoles = autoListService.getRolesByFunction("banneredit");
        model.addAttribute("bannerRoles", bannerRoles);
        String navRoles = autoListService.getRolesByFunction("navedit");
        model.addAttribute("navRoles", navRoles);
        String authoRoles = autoListService.getRolesByFunction("authoedit");
        model.addAttribute("authoRoles", authoRoles);
    }
}
