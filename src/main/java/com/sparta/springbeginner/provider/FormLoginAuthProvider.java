package com.sparta.springbeginner.provider;


import com.sparta.springbeginner.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;


public class FormLoginAuthProvider implements AuthenticationProvider {

    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public FormLoginAuthProvider(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    //Client에서 전달한 ID/PW가 DB의 ID/PW와 일치하는 지 인증
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token =
                (UsernamePasswordAuthenticationToken) authentication;
        // FormLoginFilter 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String username = token.getName();
        String password = (String) token.getCredentials();

        // UserDetailsService 를 통해 DB에서 username 으로 사용자 조회
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            //실패시 throw
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }
        //성공한다면 token만들어주기 -> 인증 성공시 FormLoginSuccessHandler통해 JWT token생성
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    /* 인증처리 가능 여부 판단기준: "인증정보의 클래스 타입"을 보고 판단
        -> 인증을 요청한 LoginFilter는 "UsernamePasswordAuthenticationToken" 인증정보를 생성하여 요청하였음
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
