package com.example.toonda.rest.comment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
@ApiModel(value = "댓글 request")
public class CommentRequestDto {

    @NotNull
    @ApiModelProperty(value="댓글 내용", required=true)
    private String comment;

}
