package com.example.CWebProj.DyNavi;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
//		model.addAllAttributes("llist 
		return "readform/textform";
	}
//	@GetMapping(value = "/form2/create/{id}")
//	public String form2create(Model model, @PathVariable("id") Integer id) {
//		model.addAttribute("MenuCate", navService.getMenu(id));
//		retrun "crateform";
//	}
//	href = 
//	
//	@PostMapping()
	
	
	
	
	//form3
	
	@GetMapping(value = "/form3/{id}")
	public String form3(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate", navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
		return "readform/imgform";
	}
	
	@GetMapping(value = "/form3/create/{id}")
	public String form3create(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("MenuCate",navService.getMenu(id));
		model.addAttribute("sidebar", navService.getSidebar(id));
		
		return "createform/img_create_form";
	}
	@PostMapping("/form3/create")
	public String form3create(@PathVariable("menuId") Integer menuId,
			@RequestParam("title") String title,
			@RequestParam("multipartFiles") List<MultipartFile> multipartFiles) throws IOException {
		System.out.println("asdf");
		Board board = new Board();
		board.setMenuId(menuId);
		board.setTitle(title);
		
		boardService.create(board, multipartFiles);
		
		return "redirect:/form3/"+menuId;
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

