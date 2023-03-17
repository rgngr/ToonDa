package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "로그인 response")
public class LoginResponseDto {

    @Schema (description = "userId")
    private Long userId;
    @Schema (description = "유저네임")
    private String username;
    @Schema (description = "프로필 사진")
    private String userImg;

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userImg = user.getImg();
    }

}
