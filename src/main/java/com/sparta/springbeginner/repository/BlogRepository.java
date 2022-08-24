package com.sparta.springbeginner.repository;

import com.sparta.springbeginner.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaAuditing // -> GET 405 메소드 에러 뜰때 꼭 있어야함
public interface BlogRepository extends JpaRepository <Blog, Long> {
    Optional<Blog> findById(Long id);
    List<Blog> findAllByOrderByModifiedAtDesc();
}
