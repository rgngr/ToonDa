package com.example.toonda.rest.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public void addComment(CommentResponseDto responseDto) {
        commentList.add(responseDto);
    }

}
