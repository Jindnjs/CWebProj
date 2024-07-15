package com.example.CWebProj.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/board")
	public String board_smaple() {
		return "board_sample";
	}
	
	@GetMapping("/board/list")
	public String list(Model model) {
		List<Board> boardList = this.boardService.getList();
		model.addAttribute("boardList", boardList);
		return "board_sample";
	}
	
	@GetMapping("/board/detail")
	public String board_detail() {
		return "board_detail";
	}
}
