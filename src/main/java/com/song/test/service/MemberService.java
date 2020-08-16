package com.song.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.song.test.dto.MemberDTO;
import com.song.test.dto.MemberDetail;
import com.song.test.mapper.dao.MemberDAO;
import com.song.test.security.Role;
import com.song.test.security.WebSecurityConfig;

import lombok.AllArgsConstructor;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	MemberDAO memberDAO;

//	PasswordEncoder passwordEncoder= new StandardPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		MemberDTO dto = memberDAO.findByID(id);
		System.out.println("############dto"+dto);
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (dto.getRole().equals(Role.ADMIN.getValue())) {
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		}

		System.out.println("~~~~~~~~authorities"+authorities);
//		return new User(dto.getId(), dto.getPw(), authorities);
		return new MemberDetail(dto);
	}
	
	public PasswordEncoder passwordEncoder() 
	{
		return new StandardPasswordEncoder(); //  // sha256 = new StandardPasswordEncoder();
	}

	public void joinUser(MemberDTO memberDto) {
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder(); // sha256
		memberDto.setPw(passwordEncoder().encode(memberDto.getPw()));

		if (memberDto.getId().equals("admin")) {
			memberDto.setRole(Role.ADMIN.getValue());
		} else {
			memberDto.setRole(Role.MEMBER.getValue());
		}
		System.out.println(memberDto);
		memberDAO.joinUser(memberDto);
	}
	
	public MemberDetail getLoginUserDetails(String id)
	{
		return (MemberDetail)loadUserByUsername(id);
	}
	
	/*
	 * 현재 인증된 세션인지 확인
	 */
	public boolean isAuthenticated() 
	{
		return SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
	
	/*
	 * 현재 인증된 세션의 정보를 가져옴
	 */
	public Authentication getAuthenicated() 
	{
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/*public UserDetails validateMember(String id, String pw) {

		UserDetails user = loadUserByUsername(id);
		if (passwordEncoder.matches(pw, user.getPassword())) {

			return user;
		} else {
			return null;
		} // return memberDAO.validateMember(map);
	}*/

}
