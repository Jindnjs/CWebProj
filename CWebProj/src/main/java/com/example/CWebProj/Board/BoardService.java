package com.example.CWebProj.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	@Autowired	
	private BoardRepository boardRepository;
	public List<Board> getList() {
		return this.boardRepository.findAll();
	}
}
