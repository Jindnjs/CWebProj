package com.example.CWebProj.Board;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AwsBucket.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private final S3Service s3Service;

	public void create(Board board, List<MultipartFile> multipartFiles) throws IOException {
		
		for (MultipartFile multipartFile : multipartFiles) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + multipartFile.getOriginalFilename();
			
			String imgurl = s3Service.uploadFile(multipartFile, fileName);
			
			board.getImageLinks().add(imgurl);
		}
		boardRepository.save(board);
	}

}
