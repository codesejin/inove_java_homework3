package com.sparta.springbeginner.security;

import com.sparta.springbeginner.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//폼로그인이 성공했을시 여기로 들어옴
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        // Token 생성 : JWT가 만들어진다
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        // 토큰을 header에 담기로함
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);
    }
}

