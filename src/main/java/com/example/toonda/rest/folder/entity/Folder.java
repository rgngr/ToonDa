package com.example.toonda.rest.folder.entity;

import com.example.toonda.config.model.TimeStamped;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
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

    @Column(nullable = false)
    private String img;

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
        this.open = requestDto.isOpen();

    }

    public void updateFolder(FolderRequestDto.Update requestDto, String img) {
        this.title = requestDto.getTitle();
        this.img = img;
        this.open = requestDto.isOpen();
    }

    public void deleteFolder() {
        this.deleted = true;
    }

    public void plusDiaryNum() {
        this.diaryNum++;
    }

}
