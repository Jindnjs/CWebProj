package com.example.CWebProj.Mail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SendResetPasswordEmailReq {
	@NotBlank
	@Email
	private String email;
}