package com.example.CWebProj.Board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

	private final BoardService boardService;
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
	@GetMapping("/create")
	public String boardcreate() {
		return "createform/board_create_prac";
	}
	
	@PostMapping("/create")
	public String boardcreate(@ModelAttribute("board") Board board){
		boardService.boardcreate(board);
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public String boardlist(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Board> paging = boardService.getBoardlist(page);
        model.addAttribute("paging", paging);
		return "readform/text_form_prac";
	}
	
	@GetMapping("/detail/{id}")
	public String boarddetail(Model model, @PathVariable("id") Integer id) {
		Board board = boardService.getBoard(id);
		model.addAttribute("board", board);
		model.addAttribute("downpath", "https://" + downpath);
		return "readform/text_form_detail_prac";
	}
	
	
}
