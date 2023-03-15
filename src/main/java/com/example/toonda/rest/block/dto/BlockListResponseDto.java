package com.example.toonda.rest.block.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BlockListResponseDto {

    private List<BlockResponseDto> blockList = new ArrayList<>();

    public void  addBlockedUser(BlockResponseDto responseDto) {
        blockList.add(responseDto);
    }

    public BlockListResponseDto() {

    }

}
