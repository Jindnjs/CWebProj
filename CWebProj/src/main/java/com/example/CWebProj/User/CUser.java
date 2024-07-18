package com.example.CWebProj.User;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String username; // 아이디
	private String password; // 비밀번호
	private boolean enabled; //
	private String role; // 권한

	@Column(unique = true)
	private String cemail; // 이메일
	private String cname; //이름
	private LocalDateTime cdate; // 날짜
	
}