package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.diary.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryListResponseDto {

    private List<Diaries> diarieList = new ArrayList<>();

    public void addDiary(Diaries responseDto) {
        diarieList.add(responseDto);
    }

    @Getter
    public static class Diaries {

        private Long diaryId;
        private String diaryImg;
        private String content;
        private LocalDate date;
        private String folderTitle;

        public Diaries(Diary diary) {
            this.diaryId = diary.getId();
            this.diaryImg = diary.getImg();
            this.content = diary.getContent();
            this.date = diary.getDate();
            this.folderTitle = diary.getFolder().getTitle();
        }

    }

}
