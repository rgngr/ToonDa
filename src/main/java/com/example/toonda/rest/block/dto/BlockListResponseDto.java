package com.example.toonda.rest.block.dto;

import com.example.toonda.rest.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BlockListResponseDto {

    private List<BlockResponseDto> blockList = new ArrayList<>();

    public void  addBlockedUser(BlockResponseDto responseDto) {
        blockList.add(responseDto);
    }

    @Getter
    public static class BlockResponseDto {

        private Long userId;
        private String userImg;
        private String username;

        public BlockResponseDto(User user) {
            this.userId = user.getId();
            this.userImg = user.getImg();
            this.username = user.getUsername();
        }
    }

}
