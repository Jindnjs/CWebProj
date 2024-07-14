package com.example.CWebProj.banner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AwsBucket.S3Service;

import jakarta.annotation.PostConstruct;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private S3Service s3Service;

    @PostConstruct
    public void initializeBanners() throws IOException {
        List<Banner> existingBanners = bannerRepository.findAll();
        if (existingBanners.isEmpty()) {
            for (int i = 1; i <= 7; i++) {
                Banner banner = new Banner();
                bannerRepository.save(banner);
            }
        }
    }

    public void update(Banner banner, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File tempFile = new File(file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }
            s3Service.uploadFile(tempFile.getAbsolutePath(), fileName);
            tempFile.delete();
            banner.setImage(fileName);
        }
        bannerRepository.save(banner);
    }

    public Banner read(Integer id) {
        return bannerRepository.findById(id).orElse(null);
    }

    public List<Banner> readlist() {
        return bannerRepository.findAll();
    }
}
