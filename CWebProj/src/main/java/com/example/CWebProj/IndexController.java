package com.example.CWebProj;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CWebProj.AutoList.AutoListService;
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
	
	private final AutoListService autoListService;
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;
	
	@GetMapping("/")
	public String index(Model model) {
		String url = "https://maps.googleapis.com/maps/api/js?key=" + googleMapsApiKey + "&loading=async&callback=initMap";
		model.addAttribute("mapsApiUrl", url);
		model.addAttribute("news", this.boardService.indexBoard(8));
		model.addAttribute("weekly", this.boardService.indexBoard(4));
		model.addAttribute("free", this.boardService.indexBoard(9));
		model.addAttribute("image", this.boardService.indexBoard(10));
		
		String bannerRoles = autoListService.getRolesByFunction("banneredit");
        model.addAttribute("bannerRoles", bannerRoles);
        String navRoles = autoListService.getRolesByFunction("navedit");
        model.addAttribute("navRoles", navRoles);
        String authoRoles = autoListService.getRolesByFunction("authoedit");
        model.addAttribute("authoRoles", authoRoles);
        
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
	
	//메인 contact
	@PostMapping("/contact")
	public String contact(@RequestParam("name") String name,@RequestParam("email") String email,
						  @RequestParam("subject") String subject,@RequestParam("message") String message) throws MessagingException {
		this.sendMailService.contactEmail(name, email, subject, message);
		return "redirect:/";
	}
	
	@PostMapping("/nav/edit")
	public String editNav(@RequestParam("navInput") String input) {
		String[] parts = input.split("/");
		
		navService.addMenu(parts[0],parts[1]);
		
		
		return "redirect:/nav/edit";
	}
}
