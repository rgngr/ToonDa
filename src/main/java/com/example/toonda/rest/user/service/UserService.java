package com.example.toonda.rest.user.service;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.jwt.JwtUtil;
import com.example.toonda.rest.user.dto.LoginRequestDto;
import com.example.toonda.rest.user.dto.SignupRequestDto;
import com.example.toonda.rest.user.entity.User;
import com.example.toonda.rest.user.repository.UserRepository;
import com.example.toonda.rest.user.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 이메일 중복 확인
    @Transactional(readOnly = true)
    public void emailCheck(String email) {
        boolean isExistEmail = userRepository.existsByEmail(email);
        if (isExistEmail) {
            throw new RestApiException(Code.OVERLAPPED_EMAIL);
        }
    }

    // 유저네임 중복 확인
    @Transactional(readOnly = true)
    public void usernameCheck(String username) {
        boolean isExistUsername = userRepository.existsByUsername(username);
        if (isExistUsername) {
            throw new RestApiException(Code.OVERLAPPED_USERNAME);
        }
    }

    // 회원가입
    @Transactional
    public void signup(SignupRequestDto requestDto) {

        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        
        userRepository.save(new User(email, username, password));

    }

    // 로그인
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByKakaoIdIsNullAndEmail(email).orElseThrow(
                () -> new RestApiException(Code.NO_USER)
        );

        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new RestApiException(Code.WRONG_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        return new LoginResponseDto(user);
    }

}
