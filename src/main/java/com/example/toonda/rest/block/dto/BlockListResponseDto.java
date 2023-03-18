package com.example.toonda.rest.block.dto;

import com.example.toonda.rest.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "차단 리스트 response")
public class BlockListResponseDto {

    private List<BlockResponseDto> blockList = new ArrayList<>();

    public void  addBlockedUser(BlockResponseDto responseDto) {
        blockList.add(responseDto);
    }

    @Getter
    @Schema(description = "차단 낱개 response")
    public static class BlockResponseDto {

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

}
