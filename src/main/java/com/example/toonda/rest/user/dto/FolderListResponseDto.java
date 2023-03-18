package com.example.toonda.rest.user.dto;

import com.example.toonda.rest.folder.entity.Folder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "마이페이지 폴더 리스트 response")
public class FolderListResponseDto {

    private List<Folders> folderList = new ArrayList<>();

    public void addFolder(Folders responseDto) {
        folderList.add(responseDto);
    }

    @Getter
    @Schema(description = "마이페이지 폴더 낱개 response")
    public static class Folders {

        private String folderImg;
        private boolean open;
        private String title;
        private Long likeNum;
        private int diaryNum;

        public Folders(Folder folder, Long likeNum) {
            this.folderImg = folder.getImg();
            this.open = folder.isOpen();
            this.title = folder.getTitle();
            this.likeNum = likeNum;
            this.diaryNum = folder.getDiaryNum();
        }

    }

}
