package com.example.CWebProj.Board;

import java.time.LocalDateTime;
import java.util.List;

import com.example.CWebProj.Comment.Comment;
import com.example.CWebProj.User.CUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
   
   
   private String author;
   
   //글 내용
   @Column(columnDefinition = "longtext")
   private String content;
   
   //업로드 날짜
   private LocalDateTime createDate;
   
   //조회수
   private Integer viewcount = 0;
   
   //공지여부
   private boolean notice;
   
   //글이 작성된 위치
   private Integer menuId;
   
   //썸네일
   private String imageLink;

   //유튜브 링크
   private String youtubeLink;
   
    //댓글
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) 
    private List<Comment> commentList;
    
    
    //댓글 수
}
//test
