package com.sparta.springbeginner.controller;

import com.sparta.springbeginner.dto.PasswordDto;
import com.sparta.springbeginner.dto.ResponseDto;
import com.sparta.springbeginner.service.BlogService;
import com.sparta.springbeginner.dto.BlogRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BlogController {
    private final BlogService blogService;

    // 생성하기
    @PostMapping("/api/blogs")
    public ResponseDto<?> ccreatBlog(@RequestBody BlogRequestDto requestDto){

        return blogService.create(requestDto);
    }
    // 전체 조회하기
    @GetMapping("/api/blogs")
    public ResponseDto<?> readBlog() {
        return blogService.readMain();
    }

    //게시글 한개 조회
    @GetMapping("/api/blogs/{id}")
    public ResponseDto<?> readOneBlog (@PathVariable Long id) {
        return blogService.readOne(id);
    }

    //수정하기
    @PutMapping("/api/blogs/{id}")
    public ResponseDto<?> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        return blogService.update(id, requestDto);
    }

    //삭제하기
    @DeleteMapping("/api/blogs/{id}")
    public ResponseDto<?> deleteBlog(@PathVariable Long id){
        return blogService.delete(id);
    }

    //비밀번호 확인
    @PostMapping("/api/blog/{id}")
    public ResponseDto<?> validateAuthorByPassword(@PathVariable Long id, @RequestBody PasswordDto password){
        return blogService.validateAuthorByPassword(id,password);
    }
}
