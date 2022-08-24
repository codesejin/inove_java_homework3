package com.sparta.springbeginner.dto;

import lombok.Getter;
import lombok.Setter;
// 디티오의 값을 셋할때 생성자로 하는게 더 편하다
@Setter
@Getter
public class BlogRequestDto {
    private String title;
    private String username;
    private String contents;

}