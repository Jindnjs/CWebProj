package com.example.CWebProj.DyNavi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;
import com.example.CWebProj.User.CUser;
import com.example.CWebProj.User.CUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FormController {
	
	private final NavService navService;
	private final BoardService boardService;
	private final CUserService cuserService;
	
	@Value("${google.maps.api.key}")
    private String googleMapsApiKey;
	
	//form1
	@GetMapping(value = "/form1/{menuId}")
	public String form1(Model model, @PathVariable("menuId") Integer menuId) {
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		System.out.println( navService.getMenu(menuId));
		if(navService.getMenu(menuId).getCategoryName().equals("교회 소개")||navService.getMenu(menuId).getCategoryName().equals("예배 안내")) {
			model.addAttribute("board", this.boardService.getform1(menuId));
		}
		else if(menuId == 3) {
			String url = "https://maps.googleapis.com/maps/api/js?key=" + googleMapsApiKey + "&loading=async&callback=initMap";
			model.addAttribute("mapsApiUrl", url);
		}
		
		return "readform/bodyform";
	}
	
	@GetMapping(value = "/form1/{menuId}/update/{boardId}")
	public String updateform1(Model model,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		
	    CUser currentUser = cuserService.authen(); 
	    Board board = boardService.getboard(boardId);
	    
	    boolean isAdminOrManager = currentUser != null && (
	        currentUser.getRole().contains("ROLE_ADMIN") || 
	        currentUser.getRole().contains("ROLE_MANAGER")
	    );
	    

	    if (!isAdminOrManager) {
	        return "redirect:/";
	    }
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", board);
		return "createform/update_test";
	}
	@PostMapping(value = "/form1/{menuId}/update/{boardId}")
	public String updateform1(@ModelAttribute Board board,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		this.boardService.updateboard(board);
		return "redirect:/form1/"+menuId;
	}
	
	
	//form2
	
	
	@GetMapping(value = "/form2/{menuId}")
	public String form2(Model model, @PathVariable("menuId") Integer menuId,@RequestParam(value="page", defaultValue = "0") int page) {		
		List<Board> notices = boardService.getNotices(menuId);
		Page<Board> paging = boardService.getPageList(menuId, page);
		
	    model.addAttribute("MenuCate", navService.getMenu(menuId));
	    model.addAttribute("sidebar", navService.getSidebar(menuId));
	    model.addAttribute("notices", notices); 
	    
	    model.addAttribute("paging", paging);

		return "readform/textform";
		}
		@GetMapping(value = "/form2/create/{menuId}")
		public String form2create(Model model, @PathVariable("menuId") Integer menuId) {
			
		     CUser currentUser = cuserService.authen();
		        
	         boolean isAdminOrManager = currentUser != null && (
	            currentUser.getRole().contains("ROLE_ADMIN") || 
	            currentUser.getRole().contains("ROLE_MANAGER")
	        );
	         
	        if (!(navService.getMenu(menuId).getCategoryName().equals("자유게시판")) && !isAdminOrManager) {
	            return "redirect:/";
	        }
			model.addAttribute("MenuCate", navService.getMenu(menuId));
			model.addAttribute("sidebar", navService.getSidebar(menuId));
			model.addAttribute("currentCUser", cuserService.authen());
			return"createform/textcreateform";
		}
	@PostMapping(value = "/form2/create/{menuId}")
	public String form2create(@PathVariable("menuId") Integer menuId,@ModelAttribute Board board) {
		this.boardService.boardcreate(menuId, board);
		return "redirect:/form2/"+menuId;
	}
	@GetMapping(value = "/form2/{menuId}/detail/{boardId}")
	public String form2detail(Model model, @PathVariable("menuId") Integer menuId,@PathVariable("boardId") Integer boardId){
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", this.boardService.getboard(boardId));
		model.addAttribute("currentCUser", cuserService.authen());
		boardService.incrementViewCount(boardId);
		return "readform/detail_test";
	}
	@GetMapping(value = "/form2/{menuId}/delete/{boardId}")
	public String deleteboard(@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		CUser currentUser = cuserService.authen(); 
	    Board board = boardService.getboard(boardId);
	    
	    boolean isAdminOrManager = currentUser != null && (
	        currentUser.getRole().contains("ROLE_ADMIN") || 
	        currentUser.getRole().contains("ROLE_MANAGER")
	    );
	    boolean isAuthor = board.getCuser() != null && board.getCuser().equals(currentUser); // 게시글 작성자와 현재 사용자 비교

	    if (!isAdminOrManager && !isAuthor) {
	        return "redirect:/";
	    }
		this.boardService.deleteboard(boardId);
		return "redirect:/form2/"+menuId;
	}
	@GetMapping(value = "/form2/{menuId}/update/{boardId}")
	public String updateboard(Model model,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		
	    CUser currentUser = cuserService.authen(); 
	    Board board = boardService.getboard(boardId);
	    
	    boolean isAdminOrManager = currentUser != null && (
	        currentUser.getRole().contains("ROLE_ADMIN") || 
	        currentUser.getRole().contains("ROLE_MANAGER")
	    );
	    boolean isAuthor = board.getCuser() != null && board.getCuser().equals(currentUser); // 게시글 작성자와 현재 사용자 비교

	    if (!isAdminOrManager && !isAuthor) {
	        return "redirect:/";
	    }
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", board);
		return "createform/update_test";
	}
	@PostMapping(value = "/form2/{menuId}/update/{boardId}")
	public String updateboard(@ModelAttribute Board board,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		this.boardService.updateboard(board);
		return "redirect:/form2/"+menuId+"/detail/"+boardId;
	}
	
	
	//form3
	@GetMapping(value = "/form3/{menuId}")
	public String form3(Model model, @PathVariable("menuId") Integer menuId, @RequestParam(value="page", defaultValue = "0") int page) {
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		
		Page<Board> paging = boardService.getBoards(page, menuId);
        model.addAttribute("paging", paging);
		
		return "readform/imgform";
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
		
		return "readform/youtubeform";
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
    		@RequestParam("mediaLink") String mediaLink) throws IOException {
		
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

