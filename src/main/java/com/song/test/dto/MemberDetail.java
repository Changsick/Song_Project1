package com.song.test.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class MemberDetail extends User {

	private String id;
	private String pw;
	private String name;
	private String email;
	private String address;
	private String phone;
	private String gender;
	private String role;
	
	public MemberDetail(MemberDTO user) {
		super(
				user.getId(), user.getPw(), AuthorityUtils.createAuthorityList(user.getRole())
				/////
				/*user.getLogin(),
				account.getPassword(),
				AuthorityUtils.createAuthorityList( account.getRoleId().name() )*/
			);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
