package com.example.toonda.rest.block.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.block.service.BlockService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/block")
@Tag(name = "block", description = "차단 관련 API")
public class BlockController {

    private final BlockService blockService;

    @ApiOperation("차단/취소")
    @PostMapping("/{userId}")
    public ResponseDto block(@PathVariable Long userId) {
        return blockService.block(userId);
    }

    @ApiOperation("차단 리스트")
    @PostMapping("/list")
    public ResponseDto blockList() {
        return DataResponseDto.of(blockService.blockList(), Code.GET_BLOCK_LIST.getStatusMsg());
    }

}