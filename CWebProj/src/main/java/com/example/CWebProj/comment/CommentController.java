package com.example.CWebProj.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/comment")
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/create/{id}")
	public String create(@RequestParam("content") String content,
			  			 @PathVariable("id") Integer id) {
		commentService.create(content, id);
			
		return "redirect:/board/detail/"+id;
	}
	
}
