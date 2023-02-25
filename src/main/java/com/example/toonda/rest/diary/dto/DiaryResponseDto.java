package com.example.toonda.rest.diary.dto;

import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiaryResponseDto {

    private Long diaryId;
    private Long writerId;
    private String title;
    private String subTitle;
    private String content;
    private String img;
    private LocalDate date;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean deleted;

    public DiaryResponseDto(User user, Diary diary) {
        this.diaryId = diary.getId();
        this.writerId = user.getId();
        this.title = diary.getTitle();
        this.subTitle = diary.getSubTitle();
        this.content = diary.getContent();
        this.img = diary.getImg();
        this.date = diary.getDate();
        this.createdAt = diary.getCreatedAt();
        this.modifiedAt = diary.getModifiedAt();
        this.deleted = diary.isDeleted();
    }


}
