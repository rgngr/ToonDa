package com.example.toonda.rest.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "대댓글 리스트 response")
public class RecommentListResponseDto {

    private List<RecommentResponseDto> recommentList = new ArrayList<>();

    public void addRecomment(RecommentResponseDto responseDto) {
        recommentList.add(responseDto);
    }

    public  RecommentListResponseDto() {

    }

}