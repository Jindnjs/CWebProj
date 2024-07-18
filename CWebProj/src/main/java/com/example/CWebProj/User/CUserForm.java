package com.example.CWebProj.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CUserForm {

	@Size(min = 7,max=25)
	@NotEmpty(message = "아이디는 이메일 아이디로 가입해주세요. 필수항목입니다.")
    private String username;
    
	@NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1; //비밀번호

	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2; //비밀번호 확인을 위한 거
}
