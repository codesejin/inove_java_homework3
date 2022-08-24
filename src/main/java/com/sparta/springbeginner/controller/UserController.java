package com.sparta.springbeginner.controller;

import com.sparta.springbeginner.dto.ResponseDto;
import com.sparta.springbeginner.dto.SignUpRequestDto;
import com.sparta.springbeginner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/signupapi/member/signup")
    public ResponseDto<?> registerUser(@RequestBody SignUpRequestDto requestDto){
        return userService.registerUser(requestDto);
    }
}
