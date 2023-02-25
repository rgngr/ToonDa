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
    private String category;
    private boolean open;
    private String img;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean deleted;
    private List<DiaryResponseDto> diaryList = new ArrayList<>();;

    public FolderResponseDto(Folder folder) {
        this.folderId = folder.getId();
        this.writerId = folder.getUser().getId();
        this.title = folder.getTitle();
        this.category = folder.getCategory();
        this.open = folder.isOpen();
        this.img = folder.getImg();
        this.createdAt = folder.getCreatedAt();
        this.modifiedAt = folder.getModifiedAt();
        this.deleted = folder.isDeleted();
    }

    public void addDiary(DiaryResponseDto responseDto) {
        diaryList.add(responseDto);
    }

}
