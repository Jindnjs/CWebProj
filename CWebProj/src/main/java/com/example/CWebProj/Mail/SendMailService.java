package com.example.CWebProj.Mail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CWebProj.Autho.AuthenKeyValService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SendMailService {
	
	@Value("${Spring.mail.username}")
	private String fromEmail;
	
	@Value("${Spring.reset-password-url}")
	private String resetPwUrl;
	
	private final JavaMailSender mailSender;
	
	private final AuthenKeyValService authenKeyValService;

	public String makeUuid() {
		return UUID.randomUUID().toString();
	}

	@Transactional
	public void sendResetPasswordEmail(String email) throws MessagingException {
		String uuid = makeUuid();
		String title = "[마이애미 아름다운 장로교회] 이메일 재설정 링크"; // 이메일 제목
		String content = "마이애미 아름다운 장로교회" //html 형식으로 작성
			+ "<br><br>" + "아래 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다." + "<br>"
			+ "<a href=\"" + resetPwUrl + "/" + uuid + "\">"
			+ resetPwUrl + "/" + uuid + "</a>" + "<br><br>"
			+ "해당 링크는 1시간 동안만 유효합니다." + "<br>"; //이메일 내용 삽입
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		
		helper.setFrom("areumdownchurch1@gmail.com");//설정한 네이버 메일 주소만 사용, 변조 불가
		helper.setTo(email);//수신자 메일 주소 
		helper.setSubject(title); //메일 제목
		helper.setText(content, true);//메일 내용 
		
		mailSender.send(message);
		
		long validTime = 60; // 1시간
		authenKeyValService.create(uuid, email, validTime);
		
	}
	
	//메인 contact 
	@Transactional
	public void contactEmail(String name,String email,String subject, String message) throws MessagingException {
		MimeMessage contact = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(contact, true, "utf-8");
		
		String content="송신 이메일 주소 : "+email+"<br>"+"내용 : "+message;
		helper.setFrom("areumdownchurch1@gmail.com");
		helper.setTo("bhwanoh@daum.net");//목사님 이메일로 
		helper.setSubject(name+"님께서 메일을 보냈습니다  "+"제목 : "+subject); //메일 제목
		helper.setText(content, true);//메일 내용 
		
		mailSender.send(contact);
	}
}