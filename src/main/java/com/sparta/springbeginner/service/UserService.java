package com.sparta.springbeginner.service;

import com.sparta.springbeginner.dto.ResponseDto;
import com.sparta.springbeginner.dto.SignUpRequestDto;
import com.sparta.springbeginner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sparta.springbeginner.domain.User;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<?> registerUser(SignUpRequestDto signUpRequestDto) {
        String nickname = signUpRequestDto.getNickname();
        // 닉네임 중복 체크
        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            return ResponseDto.fail("NICKNAME ALREADY EXIST", "중복된 닉네임이 존재합니다.");
        }
        /*
        닉네임 체크
        - 최소 4자이상, 최대 12자 이하
        - 알파벳 대소문자, 숫자(0~9)로만 구성
         */
        String nickPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,12}$";
        Matcher nickMatcher = Pattern.compile(nickPattern).matcher(nickname);
        if(!nickMatcher.matches()){
            return ResponseDto.fail("NICKNAME WRONG FORMAT", "4-12자의 영문 대소문자, 숫자를 사용해야 합니다.");
        }

        // 비밀번호 체크
        // 패스워드 암호화
        String password = signUpRequestDto.getPassword();
        String passwordConfirm = signUpRequestDto.getPasswordConfirm();
        /*
        비밀번호 체크
        - 최소 4자이상, 최대 32자 이하
        - 알파벳 소문자(a~z), 숫자(0~9)로만 구성
         */
        String pwPattern = "^(?=.*\\d)(?=.*[a-z]).{4,32}$";
        Matcher pwMatcher = Pattern.compile(pwPattern).matcher(password);
        if(!pwMatcher.matches()){
            //대문자까지 적용됨
            return ResponseDto.fail("PASSWORD WRONG FORMAT", "4-32자의 영문 소문자, 숫자를 사용해야 합니다.");
        }
        if (!password.equals(passwordConfirm)) {
            return ResponseDto.fail("PASSWORD MISMATCH", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");

        } else {
            signUpRequestDto.setNickname(nickname);
            signUpRequestDto.setPassword(passwordEncoder.encode(password));
            return ResponseDto.success(userRepository.save(new User(signUpRequestDto)));
        }
//        User user = new User(signUpRequestDto);
//        userRepository.save(user);
//        return ResponseDto.success(nickname);
    }
}