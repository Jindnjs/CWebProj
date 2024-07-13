package com.example.CWebProj.banner;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/banner")
public class BannerController {
	@Autowired
	public BannerService bannerService;

	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	
	@GetMapping("")
	public String banner(Model model) {
		model.addAttribute("banners", bannerService.readlist());
		return "banner";
	}
	
	
	@PostMapping("/update")
    public String update(@ModelAttribute Banner banner, @RequestParam("image") MultipartFile file) throws IOException {
        bannerService.update(banner, file);
        return "redirect:/banner";
    }
	
	@PostMapping("/create")
	@ResponseBody
	public Banner addNewBanner() {
		Banner newBanner = new Banner();
		newBanner.setNum(bannerService.getBannercnt() + 1);
		bannerService.create(newBanner);
		
		return newBanner;
	}
	
	@GetMapping("/delete")
	@ResponseBody
	public void deleteBanner(@RequestParam("id") Integer id) {
		bannerService.delete(id);
	}
	
	
}
