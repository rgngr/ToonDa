package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.diary.entity.Diary;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Schema(description = "마이페이지 다이어리 좋아요 리스트 response")
public class DiaryListResponseDto {

    private List<Diaries> diarieList = new ArrayList<>();

    public void addDiary(Diaries responseDto) {
        diarieList.add(responseDto);
    }

    @Getter
    @RequiredArgsConstructor
    @Schema(description = "마이페이지 다이어리 낱개 response")
    public static class Diaries {

        private String img;
        private String content;
        private LocalDate date;
        private String folderTitle;

        public Diaries(Diary diary) {
            this.img = diary.getImg();
            this.content = diary.getContent();
            this.date = diary.getDate();
            this.folderTitle = diary.getFolder().getTitle();
        }

    }

}
