package com.song.test.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.song.test.dto.MemberDetail;
import com.song.test.security.Role;
import com.song.test.service.MemberService;
import com.song.test.util.EncUtil;

@Controller
@RequestMapping("/email")
public class EmailController {

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	MemberService memberService;
	
	EncUtil enc = new EncUtil();
	
	@RequestMapping("/send")
	@ResponseBody
	public String sendMail() {
		String resultmsg = "";
		try {
			MimeMessage mesg = mailSender.createMimeMessage();
			MimeMessageHelper mesgHelper = new MimeMessageHelper(mesg, true, "UTF-8");
			
			MemberDetail member = memberService.getLoginUserDetails();
			Date currDay = new Date();
			String code = member.getId() + ":" + currDay.getTime();
			String encCode = enc.setEnc(code);
			System.out.println("encCode : "+encCode);
			String contents = "html 태그가 되야함...<br>"
					+ "<a href='https://localhost:8080/song_project2/email/mailCheck?send="+encCode+"'>link</a><br>"
					+ "<button>button</button>";
//			contents += "<script>";
//			contents += "function send(){window.open('https://localhost:8080/song_project2/');}";			
//			contents += "</script>";
			
			mesgHelper.setFrom("tlakffja@naver.com");
			
			mesgHelper.setSubject("title test");
			mesgHelper.setText(contents, true);
			mesgHelper.setTo("schsong@gridone.co.kr");
			mesg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(member.getEmail())); //"schsong@gridone.co.kr"
			mailSender.send(mesg);
			resultmsg = "SUCCESS";
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultmsg = "FAIL";
		}
		return resultmsg;
	}
	
	@RequestMapping("/mailCheck")
	public String checkEmailAuth(String send, Model m) {//, HttpServletRequest request
		System.out.println("send : "+send);
		send = send.replace(" ", "+");
		send = send.replace("^", "/");
		send = send.replace("&", "");
		String decode = enc.setdec(send);
		System.out.println("send dec : "+decode);
		
		String id = decode.split(":")[0];
		long userTime = Long.parseLong(decode.split(":")[1]);
		
		long serverTime = new Date().getTime();
		
		if(serverTime - userTime > (1000 * 30)) {
			m.addAttribute("mesg", "제한시간 지남");
			return "redirect:/";
		}else {
			System.out.println("get in time");
			MemberDetail member = memberService.getLoginUserDetails();
			member.setRole(Role.MEMBER.getValue());
			memberService.updateRole(member);
			//request.getSession().invalidate();
			memberService.updateAuthority(member);
			return "redirect:/";
		}
		
	}
	
	
}
