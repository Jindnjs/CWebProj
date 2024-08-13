package com.example.CWebProj.Board;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AutoList.AutoListService;
import com.example.CWebProj.Comment.Comment;
import com.example.CWebProj.DyNavi.NavService;
import com.example.CWebProj.User.CUser;
import com.example.CWebProj.User.CUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final NavService navService;
	private final BoardService boardService;
	private final CUserService cuserService;
	private final AutoListService autoListService;
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;
	
	//=================== form1 Start ===================

	@GetMapping(value = "/form1/{menuId}")
	public String form1(Model model, @PathVariable("menuId") Integer menuId) {
		
		//게시판의 예외처리
		if(!navService.chechExist("/form1", menuId))
			return "redirect:/";
				
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		
		//권한 (수정버튼)
	    model.addAttribute("ManagingRoles", autoListService.getRolesByIdAndFunc(menuId, "updatebutton"));
		
		if(navService.getMenu(menuId).getCategoryName().equals("교회 소개")||navService.getMenu(menuId).getCategoryName().equals("예배 안내")) {
			model.addAttribute("board", this.boardService.getform1(menuId));
		}
		else if(menuId == 3) {
			String url = "https://maps.googleapis.com/maps/api/js?key=" + googleMapsApiKey + "&loading=async&callback=initMap";
			model.addAttribute("mapsApiUrl", url);
		}
		
		return "form/read/bodyform";
	}
	
	
	@GetMapping(value = "/form1/{menuId}/update/{boardId}")
	public String updateform1(Model model,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
	
		//게시판의 예외처리
		if(!navService.chechExist("/form1", menuId))
			return "redirect:/";
	    //페이지 접근 권한체크
	    if (!autoListService.checkRoleByIdAndFunc(menuId, "updatebutton"))
	        return "redirect:/";
	    
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", boardService.getboard(boardId));
		
		String noticecheckbox = autoListService.getRolesByFunction("noticecheckbox");
        model.addAttribute("noticecheckbox", noticecheckbox);
        
		return "form/create/update_test";
	}
	
	
	@PostMapping(value = "/form1/{menuId}/update/{boardId}")
	public String updateform1(@ModelAttribute Board board,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		this.boardService.updateboard(board);
		return "redirect:/form1/"+menuId;
	}
	
	//==================== form1 End ====================
	
	
	//=================== form2 Start ===================
	
	@GetMapping(value = "/form2/{menuId}")
	public String form2(Model model, @PathVariable("menuId") Integer menuId,@RequestParam(value="page", defaultValue = "0") int page) {	
		
		//게시판의 예외처리
		if(!navService.chechExist("/form2", menuId))
			return "redirect:/";
				
		List<Board> notices = boardService.getNotices(menuId);
		Page<Board> paging = boardService.getPageList(menuId, page);
		
	    model.addAttribute("MenuCate", navService.getMenu(menuId));
	    model.addAttribute("sidebar", navService.getSidebar(menuId));
	    model.addAttribute("notices", notices); 
	    model.addAttribute("paging", paging);
	    
	    //textform에 작성버튼 권한 불러오기
        model.addAttribute("ManagingRoles", autoListService.getRolesByIdAndFunc(menuId, "addButton"));
        
		return "form/read/textform";
		}
	
	
	@GetMapping(value = "/form2/create/{menuId}")
	public String form2create(Model model, @PathVariable("menuId") Integer menuId) {
		
		//게시판의 예외처리
		if(!navService.chechExist("/form2", menuId))
			return "redirect:/";
		
		//권한 검사
	    if (!autoListService.checkRoleByIdAndFunc(menuId, "addbutton"))
	        return "redirect:/";
	    
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("currentCUser", cuserService.authen());
		
        model.addAttribute("noticecheckbox", autoListService.getRolesByFunction("noticecheckbox"));
        
		return"form/create/textcreateform";
	}
	
	
	@PostMapping(value = "/form2/create/{menuId}")
	public String form2create(@PathVariable("menuId") Integer menuId,@ModelAttribute Board board) {
		int noticeCount = boardService.countNotice(menuId);
	    if (noticeCount >= 5 && board.isNotice()) {
	    	board.setNotice(false);
	    	this.boardService.boardcreate(menuId, board);
	        return "redirect:/form2/" + menuId + "?error=tooManyNotices";
	    }
		this.boardService.boardcreate(menuId, board);
		
		return "redirect:/form2/"+menuId;
	}
	
	
	@GetMapping(value = "/form2/{menuId}/detail/{boardId}")
	public String form2detail(Model model, @PathVariable("menuId") Integer menuId,@PathVariable("boardId") Integer boardId){
		
		//게시판의 예외처리
		if(!navService.chechExist("/form2", menuId))
			return "redirect:/";
				
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", this.boardService.getboard(boardId));
		model.addAttribute("currentCUser", cuserService.authen());
		
		List<Comment> commentList=this.boardService.getboard(boardId).getCommentList();
		Collections.reverse(commentList); // 리스트 반전
        model.addAttribute("commentList", commentList);
		boardService.incrementViewCount(boardId);
		
		//수정 & 삭제버튼 권한 전달
        model.addAttribute("ManagingRoles", autoListService.getRolesByIdAndFunc(menuId, "editAccess"));
        model.addAttribute("commentEditRole", autoListService.getRolesByFunction("commentedit"));

		return "form/read/detail_test";
	}
	
	
	@GetMapping(value = "/form2/{menuId}/delete/{boardId}")
	public String deleteboard(@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		//게시판의 예외처리
		if(!navService.chechExist("/form2", menuId))
			return "redirect:/";
		
		//작성자가 아닌데 삭제권한도 없으면 /index
		CUser currentUser = cuserService.authen();
		Board board = boardService.getboard(boardId);
		if(!(board.getCuser() != null && board.getCuser().equals(currentUser))) {
			if (!autoListService.checkRoleByIdAndFunc(menuId, "editAccess"))
		        return "redirect:/";
		}

		this.boardService.deleteboard(boardId);
		return "redirect:/form2/"+menuId;
	}
	
	
	@GetMapping(value = "/form2/{menuId}/update/{boardId}")
	public String updateboard(Model model,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		
		//게시판의 예외처리
		if(!navService.chechExist("/form2", menuId))
			return "redirect:/";
		
		//작성자가 아닌데 수정권한도 없으면 /index
		CUser currentUser = cuserService.authen();
		Board board = boardService.getboard(boardId);
		if(!(board.getCuser() != null && board.getCuser().equals(currentUser))) {
			if (!autoListService.checkRoleByIdAndFunc(menuId, "editAccess"))
		        return "redirect:/";
		}
		
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", board);
        model.addAttribute("noticecheckbox", autoListService.getRolesByFunction("noticecheckbox"));

		return "form/create/update_test";
	}
	
	
	@PostMapping(value = "/form2/{menuId}/update/{boardId}")
	public String updateboard(@ModelAttribute Board board,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		int noticeCount = boardService.countNotice(menuId);
	    if (noticeCount >= 5 && board.isNotice()) {
	    	board.setNotice(false);
	    	this.boardService.boardcreate(menuId, board);
	        return "redirect:/form2/" + menuId + "?error=tooManyNotices";
	    }
		this.boardService.updateboard(board);
		return "redirect:/form2/"+menuId+"/detail/"+boardId;
	}
	
	
	//search
	
	
	@GetMapping(value = "/form2/{menuId}/search")
 	public String search(Model model, @PathVariable("menuId") Integer menuId,
 	        @RequestParam(value="page", defaultValue = "0") int page,
 	        @RequestParam("searchType") String searchType,
 	        @RequestParam("query") String query) {
 	    Page<Board> result = boardService.getResult(page, menuId, searchType, query);
 	    List<Board> notices = boardService.getNotices(menuId);

 	    model.addAttribute("MenuCate", navService.getMenu(menuId));
 	    model.addAttribute("sidebar", navService.getSidebar(menuId));

 	    model.addAttribute("notices", notices);
 	    model.addAttribute("paging", result);
 	    model.addAttribute("searchType", searchType);
 	    model.addAttribute("query", query);

 	    return "form/read/textform";
 	}

	
	//==================== form2 End ====================
	
	
	//=================== form3 Start ===================
	
	
	@GetMapping(value = "/form3/{menuId}")
	public String form3(Model model, @PathVariable("menuId") Integer menuId, @RequestParam(value="page", defaultValue = "0") int page) {
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		
		Page<Board> paging = boardService.getBoards(page, menuId);
        model.addAttribute("paging", paging);
        
        String ManagingRoles = autoListService.getManagingRoles(menuId);
        if (ManagingRoles == null || ManagingRoles.isEmpty()) {
            ManagingRoles = "'ROLE_USER'"; 
        }
        model.addAttribute("ManagingRoles", ManagingRoles);
		
		return "form/read/imgform";
	}
	
	
	@GetMapping(value = "/form3/{menuId}/detail/{boardId}")
    @ResponseBody
    public Board getBoard(Model model, @PathVariable("menuId") Integer menuId,
			@PathVariable("boardId") Integer boardId) {
		boardService.incrementViewCount(boardId);
		
        return boardService.read(menuId, boardId);
    }
	
	
	@PostMapping(value = "/form3/{menuId}/create")
	@ResponseBody
	public String form3create(Model model, @PathVariable("menuId") Integer menuId,
	                          @RequestParam("title") String title,
	                          @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
	    if (multipartFile.isEmpty()) {
	        return "이미지를 선택해주세요.";
	    }
	    if(title.equals("")) {
	    	return "제목을 지어주세요.";
	    }
	    
	    Board board = new Board();
	    board.setMenuId(menuId);
	    board.setTitle(title);
	    boardService.create(board, multipartFile);
	    return "success";
	}
	
	
	@GetMapping(value = "/form3/{menuId}/update/{boardId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> form3update(@PathVariable("menuId") Integer menuId,
	                                                      @PathVariable("boardId") Integer boardId) {
	    Map<String, Object> response = new HashMap<>();
	    response.put("menu", navService.getMenu(menuId));
	    response.put("sidebar", navService.getSidebar(menuId));
	    response.put("board", boardService.getBoard(boardId));
	    
	    return ResponseEntity.ok(response);
	}
	
	
	@PostMapping(value = "/form3/{menuId}/update/{boardId}")
    @ResponseBody
    public String form3update(@RequestParam("boardId") Integer boardId, 
    		@RequestParam("title") String title, 
    		@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        Board board = boardService.getBoard(boardId);
        board.setTitle(title);
        boardService.update(boardId, board, multipartFile);
        return "success";
    }
	
	
	@GetMapping(value = "/form3/{menuId}/delete/{boardId}")
	@ResponseBody
	public ResponseEntity<String> form3delete(@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
	    try {
	        boardService.delete(boardId);
	        return ResponseEntity.ok("success");
	    } catch (Exception e) {
	        e.printStackTrace(); // 서버 로그에 예외 출력
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the board.");
	    }
	}

	
	
	
	//form4
	
	
	@GetMapping(value = "/form4/{menuId}")
	public String form4(Model model, @PathVariable("menuId") Integer menuId, @RequestParam(value="page", defaultValue = "0") int page) {
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("page", boardService.getBoards(page, menuId));
		
		 String ManagingRoles = autoListService.getManagingRoles(menuId);
	        if (ManagingRoles == null || ManagingRoles.isEmpty()) {
	            ManagingRoles = "'ROLE_USER'"; 
	        }
	        model.addAttribute("ManagingRoles", ManagingRoles);
		
		return "form/read/youtubeform";
	}
	
	
	@PostMapping(value = "/form4/{menuId}/create")
	@ResponseBody
	public String form4create(Model model, @PathVariable("menuId") Integer menuId,
	                          @RequestParam("title") String title,
	                          @RequestParam("mediaLink") String mediaLink,
	                          @RequestParam("content") String content) throws IOException {
	    if(title.equals("")) {
	    	return "제목을 지어주세요.";
	    }
	    if(mediaLink.equals("")) {
	    	return "유튜브 url을 입력해주세요.";
	    } else {
	    	String youtubePrefix = "https://youtu.be/";
	        if(mediaLink.startsWith(youtubePrefix)) {
	            mediaLink = mediaLink.substring(youtubePrefix.length());
	            mediaLink = "https://www.youtube.com/embed/" + mediaLink;
	        }
	    }
	    
	    Board board = new Board();
	    board.setMenuId(menuId);
	    board.setTitle(title);
	    board.setMediaLink(mediaLink);
	    board.setContent(content);
	    boardService.boardcreate(menuId, board);
	    return "success";
	}
	
	
	@GetMapping(value = "/form4/{menuId}/update/{boardId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> form4update(@PathVariable("menuId") Integer menuId,
	                                                      @PathVariable("boardId") Integer boardId) {
	    Map<String, Object> response = new HashMap<>();
	    response.put("menu", navService.getMenu(menuId));
	    response.put("sidebar", navService.getSidebar(menuId));

	    Board board = boardService.getBoard(boardId);

	    if (board.getMediaLink() != null && !board.getMediaLink().startsWith("https://youtu.be/")) {
	        board.setMediaLink("https://youtu.be/" + board.getMediaLink());
	    }
	    response.put("board", board);
	    
	    return ResponseEntity.ok(response);
	}
	
	
	@PostMapping(value = "/form4/{menuId}/update/{boardId}")
    @ResponseBody
    public String form4update(@RequestParam("boardId") Integer boardId, 
    		@RequestParam("title") String title,
    		@RequestParam("mediaLink") String mediaLink,
    		@RequestParam("content") String content) throws IOException {
		
		if(title.equals("")) {
	    	return "제목을 지어주세요.";
	    }
	    if(mediaLink.equals("")) {
	    	return "유튜브 url을 입력해주세요.";
	    } else {
	    	String youtubePrefix = "https://youtu.be/";
	        if(mediaLink.startsWith(youtubePrefix)) {
	            mediaLink = mediaLink.substring(youtubePrefix.length());
	        }
	    }
		
        Board board = boardService.getBoard(boardId);
        board.setTitle(title);
        board.setMediaLink(mediaLink);
        board.setContent(content);
        boardService.updateboard(board);
        return "success";
    }
	
	
	@GetMapping(value = "/form4/{menuId}/delete/{boardId}")
	@ResponseBody
	public ResponseEntity<String> form4delete(@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
	    try {
	        boardService.delete(boardId);
	        return ResponseEntity.ok("success");
	    } catch (Exception e) {
	        e.printStackTrace(); // 서버 로그에 예외 출력
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the board.");
	    }
	}
}

