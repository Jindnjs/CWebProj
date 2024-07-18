package com.example.CWebProj.Comment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;
import com.example.CWebProj.DyNavi.MenuCateg;
import com.example.CWebProj.DyNavi.NavService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

	private final CommentService commentService;

	private final BoardService boardService;
	
	private final NavService navService;
	
	@PostMapping("/{menuId}/create/{boardId}")
	public String createComment(@PathVariable("menuId") Integer menuId,
								@PathVariable("boardId") Integer boardId,
								@RequestParam("content") String content) {
		Board board = this.boardService.getboard(boardId);
		MenuCateg menucateg = this.navService.getMenu(menuId);
		this.commentService.create(board, content);
		return "redirect:" + menucateg.getBoardLink() + "/" + menuId + "/detail/" + boardId;
	}
	
}
