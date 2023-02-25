package com.example.toonda.rest.folder.entity;

import com.example.toonda.config.model.TimeStamped;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

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

    @Column(nullable = false)
    private String category;

    @Column
    private boolean open;

    @Column
    private String img;

    @Column
    private boolean deleted = false;

    public Folder (User user, FolderRequestDto requestDto, String img) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.open = requestDto.isOpen();
        this.img = img;
    }

}
