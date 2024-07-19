package com.example.CWebProj.Mail;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendResetPasswordEmailRes {
	private String UUID;
}