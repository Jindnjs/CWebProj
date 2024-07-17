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
	private String username; // for SpringSecurity Policy 아이디
	private String password; // for SpringSecurity Policy 비밀번호
	private boolean enabled; // for SpringSecurity Policy
	private String role; // for SpringSecurity Policy 권한

	@Column(unique = true)
	private String cemail; // 이메일
	private LocalDateTime cdate; // 날짜
}