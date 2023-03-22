package com.example.toonda.rest.block.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.block.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/block")
@Api(tags = {"7) 차단"})
public class BlockController {

    private final BlockService blockService;

    @Operation(summary = "차단/취소")
    @PostMapping("/{userId}")
    public ResponseDto block(@PathVariable Long userId) {
        return blockService.block(userId);
    }

    @Operation(summary = "차단 리스트")
    @GetMapping("/list")
    public ResponseDto blockList() {
        return DataResponseDto.of(blockService.blockList(), Code.GET_BLOCK_LIST.getStatusMsg());
    }

}
