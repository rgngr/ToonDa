package com.example.toonda.rest.folder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Schema(description = "폴더 생성 request")
public class FolderRequestDto {

    @Schema(description = "제목")
    @NotNull
    private String title;

    @Schema(description = "카테고리")
    @NotNull
    private String category;

    @Schema(description = "공개 여부")
    private boolean open;

    @Getter
    @NoArgsConstructor
    @Schema(description = "폴더 수정 request")
    public static class Update {

        @Schema(description = "제목")
        @NotNull
        private String title;

        @Schema(description = "공개 여부")
        private boolean open;
    }

}
