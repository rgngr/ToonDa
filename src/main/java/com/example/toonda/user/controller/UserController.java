package com.example.toonda.user.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.user.dto.LoginRequestDto;
import com.example.toonda.user.dto.SignupRequestDto;
import com.example.toonda.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "users", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "이메일 중복 확인")
    @GetMapping(value = "/email-check/{email:.+}/")
    public ResponseDto emailCheck(@PathVariable String email) {
        userService.emailCheck(email);
        return ResponseDto.of(true, Code.EMAIL_CHECK_SUCCESS);
    }

    @ApiOperation(value = "유저네임 중복 확인")
    @GetMapping(value = "/username-check/{username}")
    public ResponseDto usernameCheck(@PathVariable String username) {
        userService.usernameCheck(username);
        return ResponseDto.of(true, Code.USERNAME_CHECK_SUCCESS);
    }

    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/signup")
    public ResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseDto.of(true, Code.USER_SIGNUP_SUCCESS);
    }

    @ApiOperation(value = "로그인")
    @PostMapping(value = "/login")
    public ResponseDto login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return DataResponseDto.of( userService.login(requestDto, response), Code.USER_LOGIN_SUCCESS.getStatusMsg());
    }

}
