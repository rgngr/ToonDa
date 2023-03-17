package com.example.toonda.rest.comment.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.comment.dto.CommentRequestDto;
import com.example.toonda.rest.comment.sevice.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
@Tag(name = "comments", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation("댓글 작성")
    @PostMapping("/{diaryId}/comments")
    public ResponseDto createComment(@PathVariable Long diaryId,
                                     @RequestBody @Valid CommentRequestDto requestDto) {
        return DataResponseDto.of(commentService.createComment(diaryId, requestDto), Code.CREATE_COMMENT.getStatusMsg());
    }

    @ApiOperation("댓글 리스트")
    @GetMapping("/{diaryId}/comments")
    public ResponseDto getComments(@PathVariable Long diaryId) {
        return DataResponseDto.of(commentService.getComments(diaryId), Code.GET_COMMENTS.getStatusMsg());
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/comments/{id}")
    public ResponseDto deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseDto.of(true, Code.DELETE_COMMENT);
    }

}
