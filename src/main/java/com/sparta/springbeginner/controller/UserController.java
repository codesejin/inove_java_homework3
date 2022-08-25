package com.sparta.springbeginner.controller;

import com.sparta.springbeginner.dto.ResponseDto;
import com.sparta.springbeginner.dto.SignUpRequestDto;
import com.sparta.springbeginner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/api/member/signup")
    public ResponseDto<?> registerUser(@RequestBody SignUpRequestDto requestDto){
        return userService.registerUser(requestDto);
    }

    //로그인

    @PostMapping("/api/member/login")
    // HttpServletResponse response 헤더를 넘겨서 헤더에 담는 것(?)
    public ResponseDto<?> loginUser(@RequestBody SignUpRequestDto requestDto, HttpServletResponse response){
        return userService.loginUser(requestDto, response);
    }


}
