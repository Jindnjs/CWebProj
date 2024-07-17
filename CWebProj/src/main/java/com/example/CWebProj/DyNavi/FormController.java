package com.example.CWebProj.DyNavi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.CWebProj.Board.Board;
import com.example.CWebProj.Board.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FormController {
	
	private final NavService navService;
	
	private final BoardService boardService;

	//form1
	@GetMapping(value = "/form1/{id}")
	public String form1(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
//		model.addAllAttributes("llist 
		return "readform/bodyform";
	}
	
	
	
	//form2
	
	@GetMapping(value = "/form2/{id}")
	public String form2(Model model, @PathVariable("id") Integer id) {
		List<Board> boards=this.boardService.getAllboard();
		List<Board> filterboards=boards.stream().filter(board->board.getMenuId()==id).collect(Collectors.toList());
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		model.addAttribute("boardList", filterboards);
		//model.addAttribute("id", id);

		return "readform/textform";
	}
	@GetMapping(value = "/form2/create/{id}")
	public String form2create(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		return"createform/textcreateform";
	}
	@PostMapping(value = "/form2/create/{menuid}")
	public String form2create(@PathVariable("menuid") Integer menuid,@ModelAttribute Board board) {
		this.boardService.boardcreate(menuid, board);
		return "redirect:/form2/"+menuid;
	}
	@GetMapping("/board/detail/{id}")
	public String form2detail(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		model.addAttribute("board", this.boardService.getboard(id));
		return "detail";
	}
	
	
	
	//form3
	
	@GetMapping(value = "/form3/{id}")
	public String form3(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
//		model.addAllAttributes("llist 
		return "readform/imgform";
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

