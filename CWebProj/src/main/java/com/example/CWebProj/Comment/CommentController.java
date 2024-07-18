package com.example.CWebProj.Comment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/{menuId}/update/{boardId}/{commentId}")
	public String updateComment(@PathVariable("commentId") Integer commentId) {
		return "";
	}
	
	@PostMapping("/update/{menuId}")
	public String updateComment(@PathVariable("menuId") Integer menuId,
								@RequestParam("commentId") Integer commentId,
								@RequestParam("commentContent") String commentContent) {
		MenuCateg menucateg = this.navService.getMenu(menuId);
		Comment comment = this.commentService.getComment(commentId);
		comment.setContent(commentContent);
		this.commentService.update(comment);
		return "redirect:" + menucateg.getBoardLink() + "/" + menuId + "/detail/" + commentService.getComment(commentId).getBoard().getId();
	}
	
	@GetMapping("/{menuId}/delete/{boardId}/{commentId}")
	public String commentDelete(@PathVariable("menuId") Integer menuId,
								@PathVariable("boardId") Integer boardId,
								@PathVariable("commentId") Integer commentId ) {
		MenuCateg menucateg = this.navService.getMenu(menuId);
		this.commentService.delete(commentId);
		return "redirect:" + menucateg.getBoardLink() + "/" + menuId + "/detail/" + boardId;
	}
	
	
}
