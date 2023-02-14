package com.example.toonda.user.dto;

import com.example.toonda.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponseDto {

    private Long userId;
    private String username;
    private String img;

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.img = user.getImg();
    }

}
