package com.example.CWebProj.Banner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AutoList.AutoListService;
import com.example.CWebProj.DyNavi.NavService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;
    
    private final NavService navService;
    
    private final AutoListService autoListService;

    @GetMapping("")
    public String banner(Model model) {
    	
    	//페이지 접근 권한체크
	    if (!autoListService.checkRoleByFunc("banneredit"))
	        return "redirect:/";
    	
        model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("sidebar", navService.getSidebar(1));
        return "admin/banner";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile file) throws IOException {
        Banner banner = bannerService.read(id);
        bannerService.update(banner, file);
        return "redirect:/banner";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBanner(@PathVariable("id") Integer id) {
        bannerService.delete(id);
        return "redirect:/banner";
    }
    
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addBanner() {
        Banner banner = new Banner();
        banner.setImage("b456786e-3b1d-4f1d-a11d-e47148e33809_1.jpg"); // 디폴트 이미지 지정
        banner.setNum((int) bannerService.count() + 1);
        bannerService.save(banner);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("id", banner.getId());
        return ResponseEntity.ok(response);
    }
}
