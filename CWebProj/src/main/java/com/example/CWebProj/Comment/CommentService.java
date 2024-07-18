package com.example.CWebProj.Comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final BoardService boardService;
	
	public void create(Board board,String content) {
		Comment comment=new Comment();
		comment.setBoard(board);
		comment.setContent(content);
		comment.setDate(LocalDateTime.now());
		this.commentRepository.save(comment);
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
