package com.example.CWebProj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;
	
	@GetMapping("/")
	public String index(Model model) {
		String url = "https://maps.googleapis.com/maps/api/js?key=" + googleMapsApiKey + "&loading=async&callback=initMap";
		model.addAttribute("mapsApiUrl", url);
		
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
}
