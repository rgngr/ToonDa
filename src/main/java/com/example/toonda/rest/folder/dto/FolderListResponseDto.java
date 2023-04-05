package com.example.toonda.rest.folder.dto;

import com.example.toonda.rest.folder.entity.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FolderListResponseDto {

    private List<Folder> folderList = new ArrayList<>();

    public void addFolder(Folder responseDto) {
        folderList.add(responseDto);
    }

    @Getter
    public static class Folder {

        private Long folderId;
        private Long userId;
        private String title;
        private String folderImg;
        private Long likeNum;
        private int diaryNum;

        public Folder(com.example.toonda.rest.folder.entity.Folder folder, Long likeNum) {
            this.folderId = folder.getId();
            this.userId = folder.getUser().getId();
            this.title = folder.getTitle();
            this.folderImg = folder.getImg();
            this.likeNum = likeNum;
            this.diaryNum = folder.getDiaryNum();
        }

    }

}
