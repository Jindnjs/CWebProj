package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.CWebProj.AwsBucket.S3Service;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private S3Service s3Service;

	public void create(Board board) {
		board.setNotice(false);
		
		board.setMenuId(1);

		board.setCreateDate(LocalDateTime.now());
		boardRepository.save(board);
		
	}

	public Board getboardByid(Integer id) {
		// TODO Auto-generated method stub

		Optional<Board> op = boardRepository.findById(id);

		if (op.isPresent()) {
			Board board = op.get();
			if (board.getViewcount() == null) {
				board.setViewcount(0);
			}
			board.setViewcount(board.getViewcount() + 1);
			this.boardRepository.save(board);
			return board;
		}

		return op.get();
	}

	public List<Board> boardlist() {
		return boardRepository.findAll();
	}

	public Page<Board> getList(int page) {
		Pageable pageable = PageRequest.of(page, 12);
		return this.boardRepository.findAll(pageable);
	}
}
