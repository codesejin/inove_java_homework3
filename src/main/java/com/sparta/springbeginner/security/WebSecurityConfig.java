package com.sparta.springbeginner.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //스프링이 처음 기동할때 설정해주는
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //패스워드 암호화 구현
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //jwt토큰 방식을 사용할때 설정 -> 스프링시큐리티가 세션을 생성하지 않을 수 있도록
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/api/member/**").permitAll()
                .anyRequest().authenticated()
                //조건 추가
                .and()
                    // [로그인 기능]
                    .formLogin()
                    // 로그인 처리 (POST /user/login)
                    .loginProcessingUrl("/api/member/login")
                    .defaultSuccessUrl("/")
                    // 로그인 처리 후 실패 시 URL
                    .failureUrl("/user/login?error")
                    .permitAll();
    }
}
