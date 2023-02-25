package com.example.toonda.rest.diary.entity;

import com.example.toonda.config.model.TimeStamped;
import com.example.toonda.rest.diary.dto.DiaryRequestDto;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@RequiredArgsConstructor
public class Diary extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="folder_id", nullable = false)
    private Folder folder;

    @Column(nullable = false)
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private boolean deleted = false;

    public Diary (User user, Folder folder, DiaryRequestDto requestDto, String img) {
        this.user = user;
        this.folder = folder;
        this.title = requestDto.getTitle();
        this.subTitle = requestDto.getSubTitle();
        this.content = requestDto.getContent();
        this.img = img;
        this.date = requestDto.getDate();
    }

    public void updateDiary(DiaryRequestDto.Update requestDto) {
        this.title = requestDto.getTitle();
        this.subTitle = requestDto.getSubTitle();
        this.content = requestDto.getContent();
        this.date = requestDto.getDate();
    }

}
