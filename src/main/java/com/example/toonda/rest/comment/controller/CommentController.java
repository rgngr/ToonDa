package com.example.toonda.rest.comment.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.comment.dto.CommentRequestDto;
import com.example.toonda.rest.comment.sevice.CommentService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
@Api(tags = {"3) 댓글"})
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    @PostMapping("/{diaryId}/comments")
    public ResponseDto createComment(@PathVariable Long diaryId,
                                     @RequestBody @Valid CommentRequestDto requestDto) {
        return DataResponseDto.of(commentService.createComment(diaryId, requestDto), Code.CREATE_COMMENT.getStatusMsg());
    }

    @Operation(summary = "댓글 리스트")
    @GetMapping("/{diaryId}/comments")
    public ResponseDto getComments(@PathVariable Long diaryId) {
        return DataResponseDto.of(commentService.getComments(diaryId), Code.GET_COMMENTS.getStatusMsg());
    }

    @Operation(summary = "댓글 삭제")
    @PatchMapping("/comments/{commentId}")
    public ResponseDto deleteComment(@PathVariable Long commentId) {
        return DataResponseDto.of(commentService.deleteComment(commentId), Code.DELETE_COMMENT.getStatusMsg());
    }

}
