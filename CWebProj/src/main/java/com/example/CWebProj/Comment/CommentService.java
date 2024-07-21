package com.example.CWebProj.Comment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;
import com.example.CWebProj.User.CUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final BoardService boardService;
	
	private final CUserService cuserService;
	
	public void create(Board board,String content,String author) {
		Comment comment=new Comment();

		comment.setBoard(board);
		comment.setContent(content);
		comment.setAuthor(author);
		comment.setDate(LocalDateTime.now());
		comment.setCuser(this.cuserService.authen());
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
