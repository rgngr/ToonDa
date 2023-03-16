package com.example.toonda.rest.block.dto;

import com.example.toonda.rest.user.entity.User;
import lombok.Getter;

@Getter
public class BlockResponseDto {

    private Long userId;
    private String userImg;
    private String username;
    // 차단 여부도 필요하려나.......?

    public BlockResponseDto(User user) {
        this.userId = user.getId();
        this.userImg = user.getImg();
        this.username = user.getUsername();
    }

}
