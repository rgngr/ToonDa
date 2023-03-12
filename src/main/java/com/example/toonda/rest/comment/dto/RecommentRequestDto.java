package com.example.toonda.rest.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Schema(description = "대댓글 작성 request")
public class RecommentRequestDto {

    @Schema(description = "대댓글 내용")
    @NotNull
    private String recomment;

}
