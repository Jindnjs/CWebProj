package com.example.CWebProj.Banner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Value("${cloud.aws.s3.endpoint}")
    private String downpath;

    @GetMapping("")
    public String banner(Model model) {
        model.addAttribute("banners", bannerService.readlist());
        model.addAttribute("downpath", "https://" + downpath);
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
