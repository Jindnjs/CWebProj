package com.example.CWebProj;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	
	private final BoardService boardService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("news", this.boardService.newboard(8));
		model.addAttribute("weekly", this.boardService.newboard(4));
		model.addAttribute("column", this.boardService.newboard(6));
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
