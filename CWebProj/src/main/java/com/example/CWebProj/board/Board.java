package com.example.CWebProj.board;

import java.util.List;

import com.example.CWebProj.comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(length=200)
	private String title;
	
	private String content;
	
	private String image1;
    
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) 
    private List<Comment> commentList;
}
