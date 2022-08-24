package com.sparta.springbeginner.service;

import com.sparta.springbeginner.domain.Blog;
import com.sparta.springbeginner.dto.PasswordDto;
import com.sparta.springbeginner.dto.ResponseDto;
import com.sparta.springbeginner.repository.BlogRepository;
import com.sparta.springbeginner.dto.BlogRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    //생성하기
    @Transactional
    public ResponseDto<?> create(BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        blogRepository.save(blog);
        return ResponseDto.success(blog);
    }

    //전체 조회하기
    @Transactional(readOnly = true)
    public ResponseDto<?> readMain() {
        return ResponseDto.success(blogRepository.findAllByOrderByModifiedAtDesc());
    }

    //하나 조회하기
    @Transactional(readOnly = true)
    public ResponseDto<?> readOne(Long id) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);

        if (optionalBlog.isEmpty()) {
            return ResponseDto.fail("NULL_BLOG_ID", "blog id isn't exist");
        }
        return ResponseDto.success(optionalBlog.get());
    }

    //수정하기
    @Transactional// 업데이트 할때 DB에반영 되어야 한다고 말해줌
    public ResponseDto<Blog> update(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        blog.update(requestDto);
        return ResponseDto.success(blog);
    }

    //삭제하기
    @Transactional
    public ResponseDto<?> delete(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        blogRepository.delete(blog);
        return ResponseDto.success(true);
    }

    // 비밀번호 확인
    @Transactional(readOnly = true)
    public ResponseDto<?> validateAuthorByPassword(Long id, PasswordDto password) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (blog.getPassword()!=password.getPassword()) {
            return ResponseDto.fail("PASSWORD_NOT_CORRECT", "password is not correct" );
        }
        return ResponseDto.success(true);
    }

    // 일치하는지 여부 판단 ..수정..오류메세지 // 암호화 복화화..
    // 컨트롤러에서dto넘기고,업데이트하면 서비스에서 일치하면 수정하고, 아닐경우 리턴 에러..트랜젝션으로
}
