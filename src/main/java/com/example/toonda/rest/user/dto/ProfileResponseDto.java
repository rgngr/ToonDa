package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private Long userId;
    private String userImg;
    private String username;
    private String Introduction;

    public ProfileResponseDto(User user) {
        this.userId = user.getId();
        this.userImg = user.getImg();
        this.username = user.getUsername();
        this.Introduction = user.getIntroduction();
    }

}
