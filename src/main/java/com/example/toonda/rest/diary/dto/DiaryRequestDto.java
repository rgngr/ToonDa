package com.example.toonda.rest.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(description = "다이어리 생성 request")
public class DiaryRequestDto {

    @Schema(description = "제목")
    @NotNull
    private String title;

    @Schema(description = "부제목")
    private String subTitle;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "날짜")
    @NotNull
    private LocalDate date;

}
