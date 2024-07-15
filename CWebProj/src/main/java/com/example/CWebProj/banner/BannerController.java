package com.example.CWebProj.banner;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("downpath", "https://" + downpath);
        return "banner";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @RequestParam("image") MultipartFile file) throws IOException {
        Banner banner = bannerService.read(id);
        bannerService.update(banner, file);
        return "redirect:/banner";
    }
}
