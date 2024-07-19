package com.example.CWebProj.Mail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SendMailService {
	
	@Value("${Spring.mail.username}")
	private String fromEmail;
	
	@Value("${Spring.reset-password-url}")
	private String resetPwUrl;
	
	JavaMailSender mailSender;

	public String makeUuid() {
		return UUID.randomUUID().toString();
	}

	@Transactional
	public void sendResetPasswordEmail(String email) {
		String uuid = makeUuid();
		String title = "[마이애미 아름다운 장로교회] 이메일 재설정 링크"; // 이메일 제목
		String content = "마이애미 아름다운 장로교회" //html 형식으로 작성
			+ "<br><br>" + "아래 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다." + "<br>"
			+ "<a href=\"" + resetPwUrl + "/" + uuid + "\">"
			+ resetPwUrl + "/" + uuid + "</a>" + "<br><br>"
			+ "해당 링크는 1시간 동안만 유효합니다." + "<br>"; //이메일 내용 삽입
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("areumdownchurch1@gmail.com");//설정한 네이버 메일 주소만 사용, 변조 불가
		message.setTo(email);//수신자 메일 주소 
		message.setSubject(title); //메일 제목
		message.setText(content);//메일 내용 
		
		mailSender.send(message);

	}
}