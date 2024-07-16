package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.List;

import com.example.CWebProj.Comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(length=200)
	private String title;
	
	private String author;
	
	private String content;
	
	private LocalDateTime createDate;
	
	private Integer viewcount;
	
	private boolean notice;
	
	private Integer menuId;
	
	private String image1;
    
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) 
    private List<Comment> commentList;
}
