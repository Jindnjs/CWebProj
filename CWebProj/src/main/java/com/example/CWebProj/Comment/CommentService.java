package com.example.CWebProj.Comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CWebProj.Board.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	
	public void commentcreate(Board board, String content) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setDate(LocalDateTime.now());
		comment.setBoard(board);
		commentRepository.save(comment);
	}
	
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
