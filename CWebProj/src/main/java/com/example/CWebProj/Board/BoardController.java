package com.example.CWebProj.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Board> boards = boardRepository.findAll();
		model.addAttribute("boards", boards);
		
		return "jihwanboardlist";
	}
	
	@GetMapping("/create")
	public String create() {
		return "jihwanboardcreate";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute Board board) {
		boardService.create(board);
		return "redirect:/board/list";
	}
	
	@GetMapping("/detail/{id}")
	public String bdetail() {
		return "boarddetail";
	}
}
