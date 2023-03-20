package com.example.toonda.rest.folder.dto;

import com.example.toonda.rest.diary.dto.DiaryResponseDto;
import com.example.toonda.rest.folder.entity.Folder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "폴더 상세페이지 response")
public class FolderResponseDto {

    @Schema(description = "폴더 Id")
    private Long folderId;
    @Schema(description = "유저 Id")
    private Long userId;
    private String userImg;
    private String username;
    private String title;
    private String folderImg;
    private List<HashtagResponseDto> hashtagList = new ArrayList<>();
    public void addHashtag(HashtagResponseDto responseDto) {
        hashtagList.add(responseDto);
    }
    private Long likeNum;
    private boolean open;
    private LocalDateTime modifiedAt;
    private List<DiaryResponseDto> diaryList = new ArrayList<>();

    public void addDiary(DiaryResponseDto responseDto) {
        diaryList.add(responseDto);
    }

    public FolderResponseDto(Folder folder, Long likeNum) {
        this.folderId = folder.getId();
        this.userId = folder.getUser().getId();
        this.userImg = folder.getUser().getImg();
        this.username = folder.getUser().getUsername();
        this.title = folder.getTitle();
        this.folderImg = folder.getImg();
        this.likeNum = likeNum;
        this.open = folder.isOpen();
        this.modifiedAt = folder.getModifiedAt();
    }

    public FolderResponseDto(Folder folder) {
        this.folderId = folder.getId();
        this.userId = folder.getUser().getId();
        this.title = folder.getTitle();
        this.folderImg = folder.getImg();
        this.open = folder.isOpen();
    }

    @Getter
    public static class Title {

        private Long folderId;
        private String title;

        public Title(Folder folder) {
            this.folderId = folder.getId();
            this.title = folder.getTitle();
        }
    }

    @Getter
    public static class FolderId {

        private Long folderId;

        public FolderId(Folder folder) {
            this.folderId = folder.getId();
        }

    }

}
