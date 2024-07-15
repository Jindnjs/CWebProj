package com.example.CWebProj.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board")
	public String index() {
		return "board_index";
	}
	
	@GetMapping("/board/create_form")
	public String create() {
		return "create_form";
	}
	
	@PostMapping("/board/create_form")
	public String create(@ModelAttribute Board board,
						 @RequestParam("file") MultipartFile file1) {
		boardService.create(board, file1);
		return "redirect:/board";
	}
	
	
}
