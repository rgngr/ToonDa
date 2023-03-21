package com.example.toonda.rest.comment.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.comment.dto.RecommentRequestDto;
import com.example.toonda.rest.comment.sevice.RecommentService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Api(tags = {"대댓글"})
public class RecommentController {

    private final RecommentService recommentService;

    @Operation(summary = "대댓글 작성")
    @PostMapping("/{commentId}/recomments")
    public ResponseDto createRecomment(@PathVariable Long commentId,
                                       @RequestBody @Valid RecommentRequestDto requestDto) {
        return DataResponseDto.of(recommentService.createRecomment(commentId, requestDto), Code.CREATE_RECOMMENT.getStatusMsg());
    }

    @Operation(summary = "대댓글 리스트")
    @GetMapping("/{commentId}/recomments")
    public ResponseDto getRecomments(@PathVariable Long commentId) {
        return DataResponseDto.of(recommentService.getRecomments(commentId), Code.GET_RECOMMENTS.getStatusMsg());
    }

    @Operation(summary = "대댓글 삭제")
    @PatchMapping("/recomments/{recommentId}")
    public ResponseDto deleteComment(@PathVariable Long recommentId) {
        recommentService.deleteRecomment(recommentId);
        return ResponseDto.of(true, Code.DELETE_RECOMMENT);
    }

}
