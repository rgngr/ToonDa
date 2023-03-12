package com.example.toonda.rest.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "다이어리 생성 request")
public class DiaryRequestDto {

    @Schema(description = "제목")
    @NotNull
    private String title;

    @Schema(description = "이미지")
    @NotNull
    private MultipartFile img;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate date;

    @Getter
    @NoArgsConstructor
    @Schema(description = "다이어리 수정 request")
    public static class Update {

        @Schema(description = "제목")
        @NotNull
        private String title;

        @Schema(description = "내용")
        private String content;

        @Schema(description = "날짜")
        @NotNull
        private LocalDate date;

    }

}
