package com.sparta.springbeginner.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springbeginner.domain.User;
import com.sparta.springbeginner.dto.ResponseDto;
import com.sparta.springbeginner.jwt.JwtProperties;
import com.sparta.springbeginner.jwt.JwtTokenUtils;
import com.sparta.springbeginner.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@NoArgsConstructor
@Component
//폼로그인이 성공했을시 여기로 들어옴
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    public FormLoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        //AccessToken 생성
        final String accessJwtToken = JwtTokenUtils.generateJwtToken(userDetails);
        //RefreshToken 생성 -> DB에 저장
        final String refreshJwtToken = JwtTokenUtils.generateREJwtToken(userDetails);

        // Response Header에 access, refresh  토근을 담아줌
        response.addHeader(JwtProperties.AUTH_HEADER, JwtProperties.TOKEN_PREFIX + accessJwtToken);
        response.addHeader(JwtProperties.REFRESH_HEADER, JwtProperties.TOKEN_PREFIX + refreshJwtToken);

        User user = userDetails.getUser();
        //refresh Token을 User DB에 넣어주기
        user.setRefreshToken(refreshJwtToken);
        userRepository.save(user);
    }
}

