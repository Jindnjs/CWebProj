package com.example.CWebProj.Comment;

import java.time.LocalDateTime;

import com.example.CWebProj.Board.Board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String content;
	
	private String image1;
	
	@ManyToOne
	private Board board;
	
	@ManyToOne
	private String writer;
	
	private LocalDateTime date;
}
