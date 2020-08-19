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
		this.id = user.getId();
		this.pw = user.getPw();
		this.name = user.getName();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.phone = user.getPhone();
		this.gender = user.getGender();
		this.role = user.getRole();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "MemberDetail [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", address=" + address
				+ ", phone=" + phone + ", gender=" + gender + ", role=" + role + "]";
	}
	
	
	
}
