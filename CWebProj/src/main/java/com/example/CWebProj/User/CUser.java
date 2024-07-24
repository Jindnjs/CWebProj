package com.example.CWebProj.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cid;

	@Column(unique = true)


	@NotBlank(message = "아이디는 필수 항목입니다")
	@Email(message = "이메일이 유효해야합니다.")
	private String username; // 아이디 -> 이메일로 가입하게끔

	@NotBlank(message = "비밀번호는 필수 항목입니다")
	private String password; // 비밀번호
	private boolean enabled; // 활성화 여부
	private String role; // 권한

	private String cname; //이름-작성자
	private LocalDateTime cdate; // 날짜
	
	//구글 로그인을 위한 정보
	private String provider;
	private String providerId;
	
	
	//프로필을 위한 정보
	private String cimage; // 프로필 사진
	private String cbirth; // 생년월일
	private String cphone; // 전화번호
	private String caddr; // 주소
	private String cinfo; //자기소개
	
	private String cjob; //직업명
	private String caffiliation; //현재소속
	
	private String cinsta; //인스타 주소
	private String cyoutube; //유튜브 주소
	private String cnaver; //네이버 주소
	
}