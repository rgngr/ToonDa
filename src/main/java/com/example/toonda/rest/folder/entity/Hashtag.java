package com.example.toonda.rest.folder.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="folder_id", nullable = false)
    private Folder folder;

    @Column
    private String hashtag;

    public Hashtag (Folder folder, String hashtag) {
        this.folder = folder;
        this.hashtag = hashtag;
    }

    public void updateHashtag (String hashtag) {
        this.hashtag = hashtag;
    }

}
