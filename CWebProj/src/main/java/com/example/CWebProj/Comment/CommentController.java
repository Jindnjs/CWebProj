package com.example.CWebProj.Comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

	private final CommentService commentService;
	private final BoardService boardService;
	
	@PostMapping("/create/{id}")
	public String commentcreate(Model model,
								@PathVariable("id") Integer id,
								@RequestParam("content") String content) {
		Board board = boardService.getBoard(id);
		commentService.commentcreate(board, content);
		return "redirect:/board/detail/" + id;
	}
}
