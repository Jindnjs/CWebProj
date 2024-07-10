package com.example.CWebProj.AwsBucket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	@Value("jindevbucket")
	private String bucketName;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public void uploadFile(MultipartFile multipartFile, String fileName) throws IOException{
		File file = new File(multipartFile.getOriginalFilename());
		
		try (FileOutputStream fos = new FileOutputStream(file)){
			fos.write(multipartFile.getBytes());
		}
//		String fileName = System.currentTimeMillis() + "_" + 
//						  multipartFile.getOriginalFilename();
		amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));
		file.delete();
	}
}
