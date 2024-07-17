package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	public void boardcreate(Board board){
		board.setCreateDate(LocalDateTime.now());
		board.setViewcount(0);
		board.setNotice(false);
		this.boardRepository.save(board);
	}
	
	public Page<Board> getBoardlist(int page){
        
		Pageable pageable = PageRequest.of(page, 12);
		return this.boardRepository.findAll(pageable);
	}
	
	public Board getBoard(Integer id) {
		Optional<Board> board = boardRepository.findById(id);
		return board.get();
	}
	
}
