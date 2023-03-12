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

    @Schema(description = "folder Id")
    private Long folderId;
    @Schema(description = "writer Id")
    private Long userId;
    private String userImg;
    private String username;
    private String title;
    private String folderImg;
    private List<HashtagResponseDto> hashtagList = new ArrayList<>();
    public void addHashtag(HashtagResponseDto responseDto) {
        hashtagList.add(responseDto);
    }
//    private Long likeNum;
    private boolean open;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean deleted;
    private List<DiaryResponseDto> diaryList = new ArrayList<>();

    public void addDiary(DiaryResponseDto responseDto) {
        diaryList.add(responseDto);
    }

    public FolderResponseDto(Folder folder) {
        this.folderId = folder.getId();
        this.userId = folder.getUser().getId();
        this.userImg = folder.getUser().getImg();
        this.username = folder.getUser().getUsername();
        this.title = folder.getTitle();
        this.folderImg = folder.getImg();
//        this.likeNum = likeNum;
        this.open = folder.isOpen();
        this.createdAt = folder.getCreatedAt();
        this.modifiedAt = folder.getModifiedAt();
        this.deleted = folder.isDeleted();
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

}
