package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "프로필 response")
public class ProfileResponseDto {

    @Schema (description = "userId")
    private Long userId;
    @Schema (description = "프로필 사진")
    private String userImg;
    @Schema (description = "유저네임")
    private String username;
    @Schema (description = "자기소개")
    private String Introduction;

    public ProfileResponseDto(User user) {
        this.userId = user.getId();
        this.userImg = user.getImg();
        this.username = user.getUsername();
        this.Introduction = user.getIntroduction();
    }

}
