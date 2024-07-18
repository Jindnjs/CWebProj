package com.example.CWebProj.DyNavi;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		this.boardService.updateboard(board);
		return "redirect:/form2/"+menuId+"/detail/"+boardId;
	}
	
	
	//form3
	
	@GetMapping(value = "/form3/{id}")
	public String form3(Model model, @PathVariable("id") Integer id, @RequestParam(value="page", defaultValue = "0") int page) {
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
		Page<Board> paging = boardService.getBoards(page);
        model.addAttribute("paging", paging);
		
		return "readform/imgform";
	}
	@GetMapping(value = "/form3/detail/{id}")
	public String form3detail(Model model, @PathVariable("id") Integer id) {
		Board board = boardService.boarddetail(id);
		model.addAttribute("sidebar", navService.getSidebar(board.getMenuId()));
		
		model.addAttribute("board", board);
		
		return "readform/img_detail_form";
	}
	@GetMapping(value = "/form3/create/{id}")
	public String form3create(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate",navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
		return "createform/img_create_form";
	}
	@PostMapping(value = "/form3/create/{id}")
	public String form3create(@PathVariable("id") Integer menuId,
			@RequestParam("title") String title,
			@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
		Board board = new Board();
		board.setMenuId(menuId);
		board.setTitle(title);
		boardService.create(board, multipartFile);
		
		return "redirect:/form3/"+menuId;
	}
	@GetMapping(value = "/form3/update/{id}")
	public String form3update(Model model, @PathVariable("id") Integer id) {
		Board board = boardService.boarddetail(id);
		model.addAttribute("sidebar", navService.getSidebar(board.getMenuId()));
		
		model.addAttribute("board", board);
		
		return "updateform/img_update_form";
	}
	@PostMapping(value = "/form3/update/{id}")
	public String form3update(@PathVariable("id") Integer id, 
	                          @ModelAttribute Board board, 
	                          @RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
	    boardService.update(id, board, multipartFile);
	    return "redirect:/form3/detail/" + id;
	}

	@GetMapping(value = "/form3/delete/{id}")
	public String form3delete(@PathVariable("id") Integer id) {
		Integer menuId = boardService.boarddetail(id).getMenuId();
		boardService.delete(id);
		
		return "redirect:/form3/" + menuId;
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

