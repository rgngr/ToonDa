package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.user.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private Long userId;
    private String userImg;
    private String username;


    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.userImg = user.getImg();
        this.username = user.getUsername();
    }

}
