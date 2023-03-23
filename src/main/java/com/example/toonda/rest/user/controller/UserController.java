package com.example.toonda.rest.user.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.user.dto.LoginRequestDto;
import com.example.toonda.rest.user.dto.SignupRequestDto;
import com.example.toonda.rest.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Api(tags = {"0) 유저"})
public class UserController {

    private final UserService userService;

    @Operation(summary = "이메일 중복 확인")
    @GetMapping("/email-check/{email}")
    public ResponseDto emailCheck(@PathVariable String email) {
        return userService.emailCheck(email);
    }

    @Operation(summary = "유저네임 중복 확인")
    @GetMapping("/username-check/{username}")
    public ResponseDto usernameCheck(@PathVariable String username) {
        return userService.usernameCheck(username);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseDto.of(true, Code.SIGNUP_SUCCESS);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseDto login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
        return DataResponseDto.of(userService.login(requestDto, response), Code.LOGIN_SUCCESS.getStatusMsg());
    }

}
