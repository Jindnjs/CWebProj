package com.example.CWebProj.Board;

import org.springframework.stereotype.Service;

import com.example.CWebProj.AwsBucket.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private final S3Service s3Service;

}
