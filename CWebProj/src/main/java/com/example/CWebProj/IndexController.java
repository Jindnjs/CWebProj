package com.example.CWebProj;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CWebProj.Banner.BannerService;
import com.example.CWebProj.Board.BoardService;
import com.example.CWebProj.DyNavi.MenuCateg;
import com.example.CWebProj.DyNavi.NavService;
import com.example.CWebProj.Mail.SendMailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	
	private final BoardService boardService;
	
	private final NavService navService;
	
	private final SendMailService sendMailService;
	
	private final BannerService bannerService;
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;
	
	@GetMapping("/")
	public String index(Model model) {
		
		//구글맵
		String url = "https://maps.googleapis.com/maps/api/js?key=" + googleMapsApiKey + "&loading=async&callback=initMap";
		model.addAttribute("mapsApiUrl", url);
		
		//게시판 & 사진
		model.addAttribute("news", this.boardService.indexBoard(8));
		model.addAttribute("weekly", this.boardService.indexBoard(4));
		model.addAttribute("free", this.boardService.indexBoard(9));
		model.addAttribute("image", this.boardService.indexBoard(10));
        
		//배너 이미지
    	model.addAttribute("banners", bannerService.readlist());

		return "index";
	}
	
	@GetMapping("/index")
	public String index1() {
		return "redirect:/";
	}
	@GetMapping("/index.html")
	public String index2() {
		return "redirect:/";
	}
	
	//메인 contact
	@PostMapping("/contact")
	public String contact(@RequestParam("name") String name,@RequestParam("email") String email,
						  @RequestParam("subject") String subject,@RequestParam("message") String message) throws MessagingException {
		this.sendMailService.contactEmail(name, email, subject, message);
		return "redirect:/";
	}

	//네비 수정
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/nav/edit")
	public String readNav(Model model) {
		
		List<MenuCateg> menuCategories = navService.getAllMenuCategories();
        Map<Integer, List<MenuCateg>> groupedMenuCategories = menuCategories.stream()
                .collect(Collectors.groupingBy(MenuCateg::getMenuRate));
        model.addAttribute("groupedMenuCategories", groupedMenuCategories);
        model.addAttribute("MenuCate", navService.getMenu(1));
		model.addAttribute("sidebar", navService.getSidebar(1));
		return "admin/nav";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/nav/edit")
	public String editNav(@RequestParam("navInput") String input) {
		String[] parts = input.split("/");
		
		navService.addMenu(parts[0],parts[1]);
		
		
		return "redirect:/nav/edit";
	}
}
