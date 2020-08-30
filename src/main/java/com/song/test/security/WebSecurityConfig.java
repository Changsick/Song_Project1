package com.song.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.song.test.service.MemberService;
import com.song.test.util.LoginFailureHandler;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
//@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
        		//BCryptPasswordEncoder();
    }
    
    @Bean
    public LoginFailureHandler loginFailureHandler() {
    	LoginFailureHandler l = new LoginFailureHandler("/main/login/error");
    	System.out.println("logine info : "+l);
    	return l;
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		.csrf().disable() // ??
        		.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/member/**").hasRole("MEMBER")
                .antMatchers("/main/**").permitAll()
                //.anyRequest().authenticated() //모든 요청에 대해, 인증된 사용자만 접근
            .and() // 로그인 설정
                .formLogin()
                .loginPage("/main/login") //user/
                .loginProcessingUrl("/user/login/chk")
                .usernameParameter("id")
                .passwordParameter("pw")
                .defaultSuccessUrl("/user/login/result",true)//"user/login/result",true
                //.failureUrl("/main/login/error")
                .failureHandler(loginFailureHandler())
                .permitAll()
            .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout/result"))
                .logoutSuccessUrl("/") // view/user/logout/result
                .invalidateHttpSession(true)
            .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/main/denied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
	
}
