package com.song.test.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Role {
		
	ADMIN("ROLE_ADMIN"),
	MEMBER("ROLE_MEMBER"), // 
	NONMOMBER("ROLE_NONMEMBER"); // 최초 가입시 권한(비회원과 같음,, 이메일 인증을 위함)

	private String value;
	
	Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
        return value;
    }
}
