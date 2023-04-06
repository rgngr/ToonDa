package com.example.toonda.rest.diary.dto;

import lombok.Getter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DiaryListResponseDto {

    private int page;
    private List<Diary> diaryList = new ArrayList<>();

    public void addDiary(Diary responseDto) {
        diaryList.add(responseDto);
    }

    public DiaryListResponseDto(int page) {
        this.page = page;
    }

    @Getter
    public static class Diary {

        private Long folderId;
        private Long diaryId;
        private String diaryImg;
        private LocalDate date;
        private String content;
        private Long commentNum;
        private Long likeNum;

        public Diary(com.example.toonda.rest.diary.entity.Diary diary, Long commentNum, Long likeNum) {
            this.folderId = diary.getFolder().getId();
            this.diaryId = diary.getId();
            this.diaryImg = diary.getImg();
            this.date = diary.getDate();
            this.content = diary.getContent();
            this.commentNum = commentNum;
            this.likeNum = likeNum;
        }

    }

}
