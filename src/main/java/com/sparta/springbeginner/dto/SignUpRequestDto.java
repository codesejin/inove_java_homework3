package com.sparta.springbeginner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private String nickname;
    private String password;
    private String passwordConfirm;
}
