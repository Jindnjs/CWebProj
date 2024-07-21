package com.example.CWebProj.Banner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AwsBucket.S3Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private S3Service s3Service;
    
    public void save(Banner banner) {
        bannerRepository.save(banner);
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
    	reorderBanners();
        return bannerRepository.findAll();
    }
    
    @Transactional
    public void delete(Integer id) {
        bannerRepository.deleteById(id);
        reorderBanners();
    }
    
    @Transactional
    public void reorderBanners() {
        List<Banner> banners = bannerRepository.findAllByOrderByNumAsc();
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            banner.setNum(i + 1);
            bannerRepository.save(banner);
        }
    }
    
    public long count() {
    	return bannerRepository.count();
    }
}
