package com.example.CWebProj.User;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
	@NotBlank(message = "이메일은 필수 항목입니다")
	@Email(message = "이메일이 유효해야합니다.")
	private String username; // 아이디 -> 이메일로 가입하게끔

	@NotBlank(message = "비밀번호는 필수 항목입니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
	private String password; // 비밀번호
	private boolean enabled; // 활성화 여부
	private String role; // 권한

	private String cname; //이름-작성자
	private LocalDateTime cdate; // 날짜
	
	//구글 로그인을 위한 정보
	private String provider;
	private String providerId;
}