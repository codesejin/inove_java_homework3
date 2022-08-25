package com.sparta.springbeginner.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Filter가 인증에 필요한 정보를 적합한 클래스형태로 만들어 Spring Security에 인증 요청함
//Spring Security는 Filter가 요청한 인증 처리 할 수 있는 Prover를 찾고, 실제 인증처리는 Provider에 의해 진행됨
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {
//UsernamePasswordAuthenticationFilter : 아이디, 패스워드 기반의 인증을 담당하는 필터
//아이디, 패스워드를 form데이터로 받아서 처리
    //jackson라이브러리의 ObjectMapper 클래스
    //json컨텐츠를 java객체로 역직렬화하거나 java객체를 json으로 직렬화 할때 사용
    // 이 파일에선 제이슨 -> 자바 객체
    final private ObjectMapper objectMapper;
    //AuthenticationManager은 인증담당
    public FormLoginFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            //readTree()메소드는 json문자열을 받아서 JsonNode객체를 리턴한다
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            String username = requestBody.get("nickname").asText();
            String password = requestBody.get("password").asText();
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (Exception e) {
            throw new RuntimeException("username, password 입력이 필요합니다. (JSON)");
        }

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

