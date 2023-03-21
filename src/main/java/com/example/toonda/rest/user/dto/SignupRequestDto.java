package com.example.toonda.rest.user.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;

@Getter
@RequiredArgsConstructor
@ApiModel(value = "회원가입 request")
public class SignupRequestDto {

    @Email
    @NotBlank
    @ApiModelProperty(value="이메일", required=true)
    private String email;

    @Size(min = 2, max = 10)
    @NotBlank
    @ApiModelProperty(value="유저네임", required=true)
    private String username;

    @Size(min = 8, max = 15)
    @Pattern (regexp="^.(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+()_*=]).*$")
    @ApiModelProperty(value="패스워드", required=true)
    private String password;

}
