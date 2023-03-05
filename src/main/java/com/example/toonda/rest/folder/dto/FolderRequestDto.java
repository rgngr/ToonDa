package com.example.toonda.rest.folder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "폴더 생성 request")
public class FolderRequestDto {

    @Schema(description = "제목")
    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "이미지")
    @NotNull(message = "이미지를 선택해주세요.")
    private MultipartFile img;

    @Schema(description = "해시태그")
    private List<String> hashtags;

    @Schema(description = "공개 여부")
    private boolean open;

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "폴더 수정 request")
    public static class Update {

        @Schema(description = "제목")
        @NotNull(message = "제목을 입력해주세요.")
        private String title;

        @Schema(description = "이미지")
        private MultipartFile img;

        @Schema(description = "해시태그")
        private List<String> hashtags;

        @Schema(description = "공개 여부")
        private boolean open;

    }

}
