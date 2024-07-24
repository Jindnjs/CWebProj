package com.example.CWebProj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	
	private final BoardService boardService;
	
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
