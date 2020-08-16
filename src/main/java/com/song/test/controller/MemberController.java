package com.song.test.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.song.test.dto.MemberDTO;
import com.song.test.security.Role;
import com.song.test.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// git push test

    // 메인 페이지
    @RequestMapping(value = {"/","/main"})
    public String main(Model m) {
    	System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "view/main/main";
    }

    // 회원가입 페이지
    @RequestMapping(value = "/main/signup", method = RequestMethod.GET)
    public String dispSignup() {
        return "view/main/signup";
    }

    // 회원가입 처리
    @PostMapping("/main/signup")
    public String execSignup(MemberDTO memberDto, Model m) {
//    	System.out.println(memberDto);
        memberService.joinUser(memberDto);
        m.addAttribute("mesg", "회원가입 성공하였습니다. 로그인하세요");
        return "view/main/login"; //
    }

    // 로그인 페이지
    @GetMapping("/main/login")
    public String dispLogin() {
    	System.out.println("dispLogin~~~~~~~~~~~~");
        return "view/main/login";
    }
    
 // 로그인 check
    @PostMapping("/user/login/chk")
    public String execLogin() {
    	System.out.println("execLogin~~~~~~~~~~~~");
        return "redirect:/user/login/result";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() { // String id, String pw, Model m
    	User member = null;
    	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
    		member = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	}
    	System.out.println("member: "+member);
    	System.out.println("dispLoginResult~~~~~~~~~~~~");
    	return "redirect:/";

    }

    // 로그아웃 결과 페이지
    @PostMapping("/user/logout/result")
    public String dispLogout() {
    	System.out.println("@@@@@dispLogout");
        return "/";
    }

    // 접근 거부 페이지
    @GetMapping("/main/denied")
    public String dispDenied() {
        return "view/main/denied";
    }

    // 내 정보 페이지 => 평범한 유저, admin 둘다 허용하고 싶어서 방법을 몰라 분기처리함
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/user/info")
    public String dispMyInfo() {
    	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
    		return "redirect:/denied";
    	}
    	System.out.println("dispMyInfo");
        return "view/user/myInfo";
    }

    // 어드민 페이지
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String dispAdmin() {
    	System.out.println("isAuthenticated : "+memberService.isAuthenticated());
    	System.out.println("getAuthenicated : "+memberService.getAuthenicated());
        return "view/admin/admin";
    }
}

