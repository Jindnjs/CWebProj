package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.List;

import com.example.CWebProj.Comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

   //제목
   @Column(length=200)
   private String title;
   
   //작성자 > 유저로 바꿔야함
   private String author;
   
   //글 내용
   private String content;
   
   //업로드 날짜
   private LocalDateTime createDate;
   
   //조회수
   private Integer viewcount;
   
   //공지여부
   private boolean notice;
   
   //글이 작성된 위치
   private Integer menuId;
   
   //썸네일
   //private String imageLink;
   
   //앨범사진 리스트
   @ElementCollection
   private List<String> imageLinks;
   
    //댓글
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) 
    private List<Comment> commentList;
    
    //댓글 수
}
//test
