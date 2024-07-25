package com.example.CWebProj.Board;

import java.io.IOException;
import java.time.LocalDateTime;
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
	
	private final CUserService cuserService;

	private final S3Service s3Service;
	
	public List<Board> getAllboard(){
		return this.boardRepository.findAll();
	}

	//board.setCuser()는 authen으로 받기
	//if문으로 비로그인 시에는 입력칸에 적은거로 받기
	public void boardcreate(Integer menuid, Board board) {
		board.setViewcount(0);
		board.setCreateDate(LocalDateTime.now());
		board.setMenuId(menuid);
		board.setCuser(this.cuserService.authen());
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
	
	//검색기능
	public Page<Board> getResult(int page, Integer menuId, String searchType, String query) {

 		Pageable pageable = PageRequest.of(page, 9, Sort.by("createDate").descending());

 		if (searchType != null && query != null) {
 	        switch(searchType) {
 	            case "title":
 	            	return boardRepository.findByMenuIdAndNoticeFalseAndTitleContainingOrderByCreateDateDesc(menuId, query, pageable);
 	            case "author":
 	            	return boardRepository.findByMenuIdAndNoticeFalseAndAuthorContainingOrCuserCnameContainingOrderByCreateDateDesc(menuId, query, query, pageable);
 	            case "title_author":
 	            	return boardRepository.findByMenuIdAndNoticeFalseAndTitleContainingOrAuthorContainingOrCuserCnameContainingOrderByCreateDateDesc(menuId, query, query, query, pageable);
 	            default:
 	            	return boardRepository.findByMenuId(menuId, pageable);
 	        }
 	    } else {
 	    	return boardRepository.findByMenuId(menuId, pageable);
 	    }
 	}
	//검색기능
	
	public Board read(Integer menuId, Integer boardId) {
		Optional<Board> o = boardRepository.findByMenuIdAndId(menuId, boardId);
		return o.get();
	}
	
	public void create(Board board, MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + multipartFile.getOriginalFilename();
			s3Service.uploadFile(multipartFile, fileName);
			board.setMediaLink(fileName);
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
	            existingBoard.setMediaLink(fileName);
	        }

	        boardRepository.save(existingBoard);
	    } else {
	        throw new IllegalArgumentException("Invalid board ID: " + id);
	    }
	}

	public void delete(Integer id) {
		boardRepository.deleteById(id);
	}
	

	//심규성 페이징 기능 test
	public List<Board> getNotices(Integer menuId) {
		return boardRepository.findByMenuIdAndNoticeTrueOrderByCreateDateDesc(menuId);
    }
	public Page<Board> getPageList(Integer menuId, int page){
		Pageable pageable = PageRequest.of(page, 13-this.getNotices(menuId).size());
		return boardRepository.findByMenuIdAndNoticeFalseOrderByCreateDateDesc(menuId, pageable);
	}
	public List<Board> indexBoard(Integer menuId){
		return this.boardRepository.findTop4ByMenuIdOrderByCreateDateDesc(menuId);
	}
	public Board getform1(Integer menuId) {
		return this.boardRepository.findByMenuId(menuId).get();
	}
	public int countNotice(Integer menuId) {
		return this.boardRepository.countByMenuIdAndNoticeTrue(menuId);
	}

	public void incrementViewCount(Integer boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + boardId));
        board.setViewcount(board.getViewcount() + 1);
        boardRepository.save(board);
    }

}
