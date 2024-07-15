package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.List;

import com.example.CWebProj.Comment.Comment;
import com.example.CWebProj.Nav.MenuCateg;
import com.example.CWebProj.user.CUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

	// 작성 글 id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 작성 글 제목
	private String title;

	// 작성 글 내용
	private String content;

	// 첨부 이미지 파일 이름
	private String image1;

	// 작성일시
	private LocalDateTime date;

	// 글 작성자
	@ManyToOne
	private CUser cuser;
	
	// 글 메뉴, 카테고리
	private MenuCateg menuCateg;

	// 달린 댓글
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Comment> commentList;
}
