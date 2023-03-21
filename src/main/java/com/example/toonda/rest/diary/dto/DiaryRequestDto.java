package com.example.toonda.rest.diary.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@ApiModel(value = "다이어리 생성 request")
public class DiaryRequestDto {

    @NotNull
    @ApiModelProperty(value="다이어리 이미지", required=true)
    private MultipartFile img;

    @ApiModelProperty(value="다이어리 내용")
    private String content;

    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @ApiModelProperty(value="다이어리 날짜",example = "YYYY-MM-DD",required=true)
    private LocalDate date;

    @Getter
    @RequiredArgsConstructor
    @ApiModel(value = "다이어리 수정 request")
    public static class Update {

        @ApiModelProperty(value="다이어리 내용")
        private String content;

        @NotNull
        @ApiModelProperty(value="다이어리 날짜",example = "YYYY-MM-DD",required=true)
        private LocalDate date;

    }

}
