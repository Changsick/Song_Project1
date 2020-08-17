package com.song.test.controller;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/email")
public class EmailController {

	@Autowired
	JavaMailSender mailSender;
	
	@RequestMapping("/send")
	@ResponseBody
	public String sendMail() {
		String resultmsg = "";
		try {
			MimeMessage mesg = mailSender.createMimeMessage();
			MimeMessageHelper mesgHelper = new MimeMessageHelper(mesg, true, "UTF-8");
			
			String contents = "html 태그가 되야함...<br>"
					+ "<a href='#'>link</a><br>"
					+ "<button>button</button>";
			
			mesgHelper.setFrom("tlakffja@naver.com");
			mesgHelper.setSubject("title test");
			mesgHelper.setText(contents, true);
			mesgHelper.setTo("schsong@gridone.co.kr");
			mesg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("schsong@gridone.co.kr"));
			mailSender.send(mesg);
			resultmsg = "SUCCESS";
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultmsg = "FAIL";
		}
		return resultmsg;
	}
	
}
