package com.example.toonda.rest.folder.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ApiModel(value = "폴더 생성 request")
public class FolderRequestDto {

    @NotNull(message = "제목을 입력해주세요.")
    @ApiModelProperty(value="폴더 제목", required=true)
    private String title;

    @NotNull(message = "이미지를 선택해주세요.")
    @ApiModelProperty(value="폴더 이미지", required=true)
    private MultipartFile img;

    @ApiModelProperty(value="해시태그 리스트")
    private List<String> hashtags;

    @NotNull
    @ApiModelProperty(value="공개 여부", required=true)
    private boolean open;

    @Getter
    @Setter
    @RequiredArgsConstructor
    @ApiModel(value = "폴더 수정 request")
    public static class Update {

        @NotNull(message = "제목을 입력해주세요.")
        @ApiModelProperty(value="폴더 제", required=true)
        private String title;

        @ApiModelProperty(value="폴더 이미지")
        private MultipartFile img;

        @ApiModelProperty(value="해시태그 리스트")
        private List<String> hashtags;

        @NotNull
        @ApiModelProperty(value="공개 여부", required=true)
        private boolean open;

    }

}
