package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.folder.entity.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FolderListResponseDto {

    private List<Folders> folderList = new ArrayList<>();

    public void addFolder(Folders responseDto) {
        folderList.add(responseDto);
    }

    @Getter
    public static class Folders {

        private Long folderId;
        private String folderImg;
        private boolean open;
        private String title;
        private Long likeNum;
        private int diaryNum;

        public Folders(Folder folder, Long likeNum) {
            this.folderId = folder.getId();
            this.folderImg = folder.getImg();
            this.open = folder.isOpen();
            this.title = folder.getTitle();
            this.likeNum = likeNum;
            this.diaryNum = folder.getDiaryNum();
        }

    }

}
