package com.example.CWebProj.DyNavi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FormController {
	
	private final NavService navService;

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

