package com.example.toonda.rest.folder.entity;

import com.example.toonda.config.model.TimeStamped;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@RequiredArgsConstructor
public class Folder extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column
    private String img;

    @Column
    private String hashtag1;

    @Column
    private String hashtag2;

    @Column
    private String hashtag3;

    @Column
    private int diaryNum = 0;

    @Column
    private boolean open;

    @Column
    private boolean deleted = false;

    public Folder (User user, FolderRequestDto requestDto, String img) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.img = img;
        List<String> hashtags = requestDto.getHashtags();
        if (hashtags.size()==0) {
            this.hashtag1 = null;
            this.hashtag2 = null;
            this.hashtag3 = null;
        } else if (hashtags.size()==1) {
            this.hashtag1 = hashtags.get(0);
            this.hashtag2 = null;
            this.hashtag3 = null;
        } else if (hashtags.size()==2) {
            this.hashtag1 = hashtags.get(0);
            this.hashtag2 = hashtags.get(1);
            this.hashtag3 = null;
        } else {
            this.hashtag1 = hashtags.get(0);
            this.hashtag2 = hashtags.get(1);
            this.hashtag3 = hashtags.get(2);
        }
        this.open = requestDto.isOpen();

    }

    public void updateFolder(FolderRequestDto.Update requestDto, String img) {
        this.title = requestDto.getTitle();
        this.img = img;
        List<String> hashtags = requestDto.getHashtags();
        if (hashtags.size()==0) {
            this.hashtag1 = null;
            this.hashtag2 = null;
            this.hashtag3 = null;
        } else if (hashtags.size()==1) {
            this.hashtag1 = hashtags.get(0);
            this.hashtag2 = null;
            this.hashtag3 = null;
        } else if (hashtags.size()==2) {
            this.hashtag1 = hashtags.get(0);
            this.hashtag2 = hashtags.get(1);
            this.hashtag3 = null;
        } else {
            this.hashtag1 = hashtags.get(0);
            this.hashtag2 = hashtags.get(1);
            this.hashtag3 = hashtags.get(2);
        }
        this.open = requestDto.isOpen();
    }

    public void deleteFolder() {
        this.deleted = true;
    }

    public void setDiaryNum() {
        this.diaryNum++;
    }

}
