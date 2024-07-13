package com.example.CWebProj.banner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AwsBucket.S3Service;

@Service
public class BannerService {
	@Autowired
	public BannerRepository bannerRepository;
	
	@Autowired
	private S3Service s3Service;
	
	public void create(Banner banner) {
		bannerRepository.save(banner);
	}
	
	public List<Banner> readlist() {
		return bannerRepository.findAll();
	}
	
	public Banner read(Integer id) {
		Optional<Banner> o = bannerRepository.findById(id);
		return o.get();
	}
	
	public void delete(Integer id) {
		bannerRepository.deleteById(id);
	}
	
	public void update(Banner banner, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            s3Service.uploadFile(file, fileName);
            banner.setImage(fileName);
        }
        bannerRepository.save(banner);
    }
	
	public int getBannercnt() {
        return (int) bannerRepository.count();
    }
}	
