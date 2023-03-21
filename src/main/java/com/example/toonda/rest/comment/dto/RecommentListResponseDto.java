package com.example.toonda.rest.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecommentListResponseDto {

    private List<RecommentResponseDto> recommentList = new ArrayList<>();

    public void addRecomment(RecommentResponseDto responseDto) {
        recommentList.add(responseDto);
    }

}
