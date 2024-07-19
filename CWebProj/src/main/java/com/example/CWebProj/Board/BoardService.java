package com.example.CWebProj.Board;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AwsBucket.S3Service;
import com.example.CWebProj.User.CUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	
	private final CUserService cUserService;


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
	
	public void deleteboard(Integer id) {
		this.boardRepository.deleteById(id);
	}
	
	public void updateboard(Board board) {
		this.boardRepository.save(board);
	}

	public Page<Board> getBoards(int page, Integer menuId) {
		Pageable pageable = PageRequest.of(page, 9, Sort.by("createDate").descending());
        return boardRepository.findByMenuId(menuId, pageable);
    }
	
	public Board read(Integer menuId, Integer boardId) {
		Optional<Board> o = boardRepository.findByMenuIdAndId(menuId, boardId);
		return o.get();
	}
	
	public void create(Board board, MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + multipartFile.getOriginalFilename();
			s3Service.uploadFile(multipartFile, fileName);
			board.setImageLink(fileName);
		}
		board.setCreateDate(LocalDateTime.now());
		
		boardRepository.save(board);
	}
	
	public void update(Integer id, Board updatedBoard, MultipartFile multipartFile) throws IOException {
	    Optional<Board> optionalBoard = boardRepository.findById(id);
	    if (optionalBoard.isPresent()) {
	        Board existingBoard = optionalBoard.get();
	        existingBoard.setTitle(updatedBoard.getTitle());
	        existingBoard.setCreateDate(updatedBoard.getCreateDate());
	        
	        if (!multipartFile.isEmpty()) {
	            UUID uuid = UUID.randomUUID();
	            String fileName = uuid + "_" + multipartFile.getOriginalFilename();
	            s3Service.uploadFile(multipartFile, fileName);
	            existingBoard.setImageLink(fileName);
	        }

	        boardRepository.save(existingBoard);
	    } else {
	        throw new IllegalArgumentException("Invalid board ID: " + id);
	    }
	}
	
	public void delete(Integer id) {
		boardRepository.deleteById(id);
	}
	
	public void incrementViewCount(Integer boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + boardId));
        board.setViewcount(board.getViewcount() + 1);
        boardRepository.save(board);
    }
}
