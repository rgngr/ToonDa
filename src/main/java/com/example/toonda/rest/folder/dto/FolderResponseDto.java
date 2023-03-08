package com.example.toonda.rest.folder.dto;

import com.example.toonda.rest.diary.dto.DiaryResponseDto;
import com.example.toonda.rest.folder.entity.Folder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FolderResponseDto {

    private Long folderId;
    private Long writerId;
    private String title;
    private String img;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
    private boolean open;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean deleted;
    private List<DiaryResponseDto> diaryList = new ArrayList<>();

    public FolderResponseDto(Folder folder) {
        this.folderId = folder.getId();
        this.writerId = folder.getUser().getId();
        this.title = folder.getTitle();
        this.hashtag1 = folder.getHashtag1();
        this.hashtag2 = folder.getHashtag2();
        this.hashtag3 = folder.getHashtag3();
        this.open = folder.isOpen();
        this.img = folder.getImg();
        this.createdAt = folder.getCreatedAt();
        this.modifiedAt = folder.getModifiedAt();
        this.deleted = folder.isDeleted();
    }

    public void addDiary(DiaryResponseDto responseDto) {
        diaryList.add(responseDto);
    }

    @Getter
    public static class Title {
        private String title;

        public Title(Folder folder) {
            this.title = folder.getTitle();
        }
    }

}
