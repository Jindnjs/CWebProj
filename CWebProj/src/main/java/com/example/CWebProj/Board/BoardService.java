package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.CWebProj.AwsBucket.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;


	private final S3Service s3Service;
	
	public List<Board> getAllboard(){
		return this.boardRepository.findAll();
	}

	public void boardcreate(Integer menuid, Board board) {
		board.setViewcount(0);
		board.setCreateDate(LocalDateTime.now());
		board.setMenuId(menuid);
		
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
	

	public Board getboard(Integer id) {
		return this.boardRepository.findById(id).get();
	}

}
