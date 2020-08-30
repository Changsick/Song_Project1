package com.song.test.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	private String id;
	private String pw;
	private String errormsgname;
	private String defaultFailureUrl;

	public LoginFailureHandler(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 String username = request.getParameter("id");
	     String password = request.getParameter("pw");
	     String errormsg = exception.getMessage();
	        System.out.println("username : "+username);
	        System.out.println("password : "+password);
	        System.out.println("errormsg : "+errormsg);
	        System.out.println("defaultFailureUrl : "+defaultFailureUrl);

	        this.id = username;
	        this.pw = password;
	     request.setAttribute("id", username);
	     request.setAttribute("pw", password);
	     request.setAttribute("mesg", errormsg);
	 
	     request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
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

	public String getErrormsgname() {
		return errormsgname;
	}

	public void setErrormsgname(String errormsgname) {
		this.errormsgname = errormsgname;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public String toString() {
		return "LoginFailureHandler [id=" + id + ", pw=" + pw + ", errormsgname=" + errormsgname
				+ ", defaultFailureUrl=" + defaultFailureUrl + "]";
	}
	
	

}
