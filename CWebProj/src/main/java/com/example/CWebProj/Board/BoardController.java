package com.example.CWebProj.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public String index() {
		return "/board/list";
	}
	
	@GetMapping("/create_form")
	public String create() {
		return "create_form";
	}
	
	@PostMapping("/create_form")
	public String create(@ModelAttribute Board board,
						 @RequestParam("file") MultipartFile file1) {
		boardService.create(board, file1);
		return "redirect:/board/list";
	}
	
}
