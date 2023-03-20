package com.example.toonda.rest.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "댓글 리스트 response")
public class CommentListResponseDto {

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public void addComment(CommentResponseDto responseDto) {
        commentList.add(responseDto);
    }

}
