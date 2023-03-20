package com.example.toonda.rest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;

@Getter
@RequiredArgsConstructor
@Schema(description = "회원가입 request")
public class SignupRequestDto {

    @Schema(description = "이메일")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "유저네임")
    @Size(min = 2, max = 10)
    @NotBlank
    private String username;

    @Schema (description = "패스워드")
    @Size(min = 8, max = 15)
    @Pattern (regexp="^.(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+()_*=]).*$")
    private String password;

}
