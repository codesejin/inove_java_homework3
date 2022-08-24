package com.sparta.springbeginner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing// 이거 없으니까 timestamped => null값 나옴
@SpringBootApplication
public class SpringbeginnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbeginnerApplication.class, args);
    }
}
