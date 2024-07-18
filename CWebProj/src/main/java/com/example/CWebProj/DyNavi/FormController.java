package com.example.CWebProj.DyNavi;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FormController {
	
	private final NavService navService;
	private final BoardService boardService;
	
	//form1
	@GetMapping(value = "/form1/{menuId}")
	public String form1(Model model, @PathVariable("menuId") Integer menuId) {
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		
//		model.addAllAttributes("llist 
		return "readform/bodyform";
	}
	
	
	
	//form2
	
	
	@GetMapping(value = "/form2/{menuId}")
	public String form2(Model model, @PathVariable("menuId") Integer menuId) {
		List<Board> boards=this.boardService.getAllboard();
		List<Board> filterboards=boards.stream().filter(board->board.getMenuId()==menuId).collect(Collectors.toList());
		Collections.reverse(filterboards);
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("boardList", filterboards);
		//model.addAttribute("id", id);

		return "readform/textform";
		}
		@GetMapping(value = "/form2/create/{menuId}")
		public String form2create(Model model, @PathVariable("menuId") Integer menuId) {
			model.addAttribute("MenuCate", navService.getMenu(menuId));
			model.addAttribute("sidebar", navService.getSidebar(menuId));
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
		return "readform/detail_test";
	}
	@GetMapping(value = "/form2/{menuId}/delete/{boardId}")
	public String deleteboard(@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		this.boardService.deleteboard(boardId);
		return "redirect:/form2/"+menuId;
	}
	@GetMapping(value = "/form2/{menuId}/update/{boardId}")
	public String updateboard(Model model,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		model.addAttribute("MenuCate", navService.getMenu(menuId));
		model.addAttribute("sidebar", navService.getSidebar(menuId));
		model.addAttribute("board", this.boardService.getboard(boardId));
		return "createform/update_test";
	}
	@PostMapping(value = "/form2/{menuId}/update/{boardId}")
	public String updateboard(@ModelAttribute Board board,@PathVariable("menuId") Integer menuId, @PathVariable("boardId") Integer boardId) {
		this.boardService.boardcreate(menuId, board);
		return "redirect:/form2/"+menuId;
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
	
		@GetMapping(value = "/form4/{id}")
		public String form4(Model model, @PathVariable("id") Integer id) {
			model.addAttribute("MenuCate", navService.getMenu(id));
			model.addAttribute("sidebar", navService.getSidebar(id));
			
//			model.addAllAttributes("llist 
			return "readform/youtubeform";
		}
}

