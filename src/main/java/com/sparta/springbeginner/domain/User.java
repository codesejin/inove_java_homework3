package com.sparta.springbeginner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.springbeginner.dto.SignUpRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name="users")
public class User extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;
    @JsonIgnore
    @Column(nullable = false, unique = true)
    private String password;

    public User(SignUpRequestDto signUpRequestDto) {
        this.nickname = signUpRequestDto.getNickname();
        this.password = signUpRequestDto.getPassword();
        //super은 안되느닞
        this.createdAt = getCreatedAt();
        this.modifiedAt = getModifiedAt();
   }

}
