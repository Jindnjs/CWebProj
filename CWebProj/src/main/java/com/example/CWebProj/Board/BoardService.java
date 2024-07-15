package com.example.CWebProj.Board;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.AwsBucket.S3Service;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private S3Service s3Service;

	public void create(Board board, MultipartFile file1) {
		if (!file1.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName1 = uuid + "_" + file1.getOriginalFilename();

			try {
				s3Service.uploadFile(file1, fileName1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			board.setImage1(fileName1);
		}

		board.setCreateDate(LocalDateTime.now());
		boardRepository.save(board);
	}

	public Board getboardByid(Integer id) {
		// TODO Auto-generated method stub
		Optional<Board> op = boardRepository.findById(id);
		return op.get();
	}
}
