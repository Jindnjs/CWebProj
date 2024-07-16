package com.example.CWebProj.Comment;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final BoardService boardService;
	
	
	
	
	
	public Comment getComment(Integer id) {
		Optional<Comment> op = commentRepository.findById(id);
		return op.get();
	}
	
	public void update(Comment comment) {
		commentRepository.save(comment);
	}

	public void delete(Integer commentid) {
		commentRepository.deleteById(commentid);
	}
}
