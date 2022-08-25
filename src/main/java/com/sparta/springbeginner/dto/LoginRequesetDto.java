package com.sparta.springbeginner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequesetDto {
        private String username;
        private String password;
}
