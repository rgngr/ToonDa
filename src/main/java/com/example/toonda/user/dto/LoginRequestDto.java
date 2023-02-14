package com.example.toonda.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@Schema(description = "로그인 request")
public class LoginRequestDto {

    @Schema(description = "이메일")
    @Email
    @NotBlank
    private String email;

    @Schema (description = "패스워드")
    private String password;

}
