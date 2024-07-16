package com.example.CWebProj.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CWebProj.Board.BoardService;

@RequestMapping("/comment")
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BoardService boardService;
	
	
	
	@PostMapping("/create/{id}")
	public String create(@RequestParam("content") String content,
			  			 @PathVariable("id") Integer id) {
		commentService.create(content, id);
			
		return "redirect:/board/detail/"+id;
	}
	/*
	 * @GetMapping("/update/{id}") public String commentUpdate(Model
	 * model, @PathVariable("id") Integer id) { model.addAttribute("comment",
	 * commentService.getComment(id)); return "commentUpdate"; }
	 * 
	 * @PostMapping("/update/{id}") public void
	 * commentUpdate(@RequestParam("content") String content,
	 * 
	 * @PathVariable("id") Integer id) { Comment comment =
	 * commentService.getComment(id); comment.setContent(content);
	 * commentService.update(comment); return "redirect:/board/detail/" +
	 * comment.getBoard().getId(); }
	 * 
	 * @GetMapping("/delete/{boardid}/{commentid}") public String
	 * commentDelete(@PathVariable("boardid") Integer boardid,
	 * 
	 * @PathVariable("commentid") Integer commentid) {
	 * commentService.delete(commentid); return "redirect:/board/detail/" + boardid;
	 * }
	 */
}
