package com.example.CWebProj.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String username; // 아이디 -> 이메일로 가입하게끔

	private String password; // 비밀번호
	private boolean enabled; // 활성화 여부
	private String role; // 권한

	private String cname; //이름-작성자
	private LocalDateTime cdate; // 날짜
	
	//구글 로그인을 위한 정보
	private String provider;
	private String providerId;
	
	// 회원가입할때 이메일 나눠서 가입하게끔
	private String emailLocalPart;
	private String emailDomainText;
	
	//프로필을 위한 정보
	private String cbackimage; //배경사진
	private String cprofileimage; // 프로필 사진
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate cbirth; // 생년월일
	private String cphone; // 전화번호
	private String caddr; // 주소
	private String cinfo; //자기소개
	
	private String cjob; //직업명
	private String caffiliation; //현재소속
	
	private String cinsta; //인스타 주소
	private String cyoutube; //유튜브 주소
	private String cnaver; //네이버 주소
	
}