package com.example.CWebProj.Board;

import java.io.IOException;
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

	public void create(Board board, MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + multipartFile.getOriginalFilename();
			s3Service.uploadFile(multipartFile, fileName); 
			board.setImageLink(fileName);
		}
		boardRepository.save(board);

	}}
