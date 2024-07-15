package com.example.CWebProj.comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CWebProj.board.BoardService;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private BoardService boardService;
	
	public void create(String content, Integer id) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setDate(LocalDateTime.now());
		comment.setBoard(boardService.getboardByid(id));
		
		commentRepository.save(comment);
	}
	
	public Comment getComment(Integer id) {
		Optional<Comment> op = commentRepository.findById(id);
		return op.get();
	}
}