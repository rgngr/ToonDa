package com.example.toonda.rest.user.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;

@Getter
@RequiredArgsConstructor
@ApiModel(value = "로그인 request")
public class LoginRequestDto {

    @Email
    @NotBlank
    @ApiModelProperty(value="이메일", required=true)
    private String email;

    @NotNull
    @ApiModelProperty(value="패스워드", required=true)
    private String password;

}
