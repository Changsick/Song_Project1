package com.song.test.controller;

import java.util.HashMap;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.song.test.dto.MemberDetail;
import com.song.test.service.MemberService;

@Controller
@RequestMapping("/webtoon")
public class WebtoonController {
	
	@Autowired
	MemberService memberService;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String webtoonMain() {
		return "view/webtoon/main";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')" + " || hasRole('ROLE_MEMBER')" )
	@RequestMapping(value = "/webtoonUpload", method = RequestMethod.GET)
	public String webtoonUpload() {
		
		MemberDetail user = memberService.getLoginUserDetails();
		System.out.println(user);
		
		return "view/webtoon/uploadMainPreview";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')" + " || hasRole('ROLE_MEMBER')" )
	@RequestMapping(value = "/uploadMainPreview", method = RequestMethod.POST)
	@ResponseBody
	public String uploadMainPreview(@RequestParam HashMap<String, Object> map, @RequestBody HashMap<String, Object> map2) {
//		memberService
		System.out.println(map);
		System.out.println(map2);
		return "view/webtoon/uploadMainPreview";
	}
}
