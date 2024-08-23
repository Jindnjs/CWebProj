package com.example.CWebProj.User;

import java.io.IOException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.CWebProj.Autho.AuthenKeyValService;
import com.example.CWebProj.DyNavi.NavService;
import com.example.CWebProj.Mail.SendMailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CUserController {


   private final CUserService cuserService;
   
   private final SendMailService mailService;
   
   private final AuthenKeyValService authenKeyValService;
   
   private final NavService navService;
   
   @GetMapping("/signup")
   public String signup() {
      return "authentication/signup";
   }

   @PostMapping("/signup")
	public String signup(CUser cuser, Model model) {
       String email = cuser.getEmailLocalPart() + "@" + cuser.getEmailDomainText();
       cuser.setUsername(email);

       model.addAttribute("message", "회원가입 성공");
		cuserService.create(cuser);
		return "redirect:/signin";
		
	}

   @GetMapping("/signin")
   public String signin() {
      return "authentication/signin";
   }


   @GetMapping("/findid")
   public String findid() {
      return "user/findid";
   }

   @GetMapping("/resetpw")
   public String findpw() {
      return "authentication/resetpw";
   }

   //UUID 생성 및 이메일 전송
   @PostMapping("/findpw")
   public String sendResetPassword(
      @RequestParam("email") String email) throws MessagingException {      
      System.out.println("서버로 넘어온 이메일 = " + email);
      
      //이메일 주소의 유효성 검증
      //만약 없으면 회원가입창으로 보냄
      
      mailService.sendResetPasswordEmail(email);
      return "redirect:/signin";
   }
   
   
   @GetMapping("/reset/{uuid}")
   public String resetPassword(@PathVariable("uuid") String uuid) {
      String email = authenKeyValService.getValue(uuid);
      if (email == null) {
         System.out.println("redis에 이메일이 없습니다.");
         return "redirect:/signin";
      }
      return "authentication/resetpw";
   }
   
   
   @PostMapping("/reset/{uuid}")
   public String resetPassword(@PathVariable("uuid") String uuid, 
         @RequestParam("password") String password) {
      cuserService.resetPassword(uuid, password);
      return "redirect:/signin";
   }
   
   

   
   
   @GetMapping("/findpw")
   public String resetpw() {
      return "authentication/findpw";
   }
   
   
   @GetMapping("/autho")
   public String index() {
      return "autho/check";
   }
   
   
   // 관리자페이지에 접근할때
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   @GetMapping("autho/adminlist")
   public String admin(Model model) {
      model.addAttribute("cusers", cuserService.readlist());
      return "autho/adminlist";
   }

   
   //매니저 페이지에 접근할때 -> 목사님 권한
   @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
   @GetMapping("/autho/managerlist")
   public String manager(Model model) {
      model.addAttribute("cusers", cuserService.getAllNonAdminUsers());
      return "autho/managerlist";
   }

   // 유저 정보 가져오기
   @GetMapping("/user/userdetail/{cid}")
   public String detail(Model model, @PathVariable("cid") Integer cid) {

      model.addAttribute("cuser", cuserService.readdetail(cid));

      return "user/userdetail";
   }
   
   // 매니저 유저 정보 가져오기
   @GetMapping("/user/userdetail_manager/{cid}")
   public String detail_manager(Model model, @PathVariable("cid") Integer cid) {

      model.addAttribute("cuser", cuserService.readdetail(cid));

      return "user/userdetail_manager";
   }
   
   
	/*
	 * // 관리자 유저 정보 업데이트
	 * 
	 * @PostMapping("/admin/update") public String update(@ModelAttribute CUser
	 * cuser) { cuserService.update(cuser); return "redirect:/autho/adminlist"; }
	 * 
	 * // 매니저 유저 정보 업데이트
	 * 
	 * @PostMapping("/manager/update") public String update_manager(@ModelAttribute
	 * CUser cuser) { cuserService.update(cuser); return
	 * "redirect:/autho/managerlist"; }
	 */

   
   //본인 프로필 접속
   @GetMapping("/profile")
   public String profile(Model model) {
      model.addAttribute("MenuCate", navService.getMenu(1));
      model.addAttribute("sidebar", navService.getSidebar(1));
      model.addAttribute("cuser", cuserService.authen());
      
      return "form/read/profile";
   }

   
   // 프로필 정보 업데이트
   @PostMapping("/profile/update")
   public String updateProfile(@ModelAttribute CUser cuser,
         @RequestParam("newPassword") String newPassword,
         @RequestParam("inputback") MultipartFile back,
         @RequestParam("inputprofile") MultipartFile profile) throws IOException {

      cuserService.profileupdate(cuser, newPassword, back, profile);
      
      return "redirect:/profile";
   }
   
   
   // 권한 페이지에서 프로필 접속하기
   @GetMapping("/profile/{username}")
   @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER') or #username == authentication.name")
   public String profile_get(Model model, @PathVariable("username") String username) {
       model.addAttribute("MenuCate", navService.getMenu(1));
       model.addAttribute("sidebar", navService.getSidebar(1));
       model.addAttribute("cuser", cuserService.getUSer(username));
       
       return "form/read/profile";
   }
   
   
   	// 프로필 정보 업데이트
	@PostMapping("/profile/update_manage/{username}")
	public String updateProfilemanage(Model model, @ModelAttribute CUser cuser, @PathVariable("username") String username,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("inputback") MultipartFile back,
			@RequestParam("inputprofile") MultipartFile profile) throws IOException {

	    model.addAttribute("cuser", cuserService.getUSer(username));
		cuserService.profileupdate(cuser, newPassword, back, profile);

		return "redirect:/profile/{username}";
	}
   
}
