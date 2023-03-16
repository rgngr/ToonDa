package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponseDto {

    private Long userId;
    private String username;
    private String userImg;

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.userImg = user.getImg();
    }

}
