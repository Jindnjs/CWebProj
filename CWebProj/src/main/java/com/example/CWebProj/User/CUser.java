package com.example.CWebProj.User;

import java.time.LocalDateTime;

import com.example.CWebProj.Board.Board;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;

	@Column(unique = true)
	@Email(message = "이메일이 유효해야합니다.")
	private String username; // 아이디 -> 이메일로 가입하게끔

	private String password; // 비밀번호
	private boolean enabled; //
	private String role; // 권한

	private String cname; //이름-작성자
	private LocalDateTime cdate; // 날짜
	


}