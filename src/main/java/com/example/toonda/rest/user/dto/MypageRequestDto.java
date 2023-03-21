package com.example.toonda.rest.user.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MypageRequestDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    @ApiModel(value = "프로필 이미지 변경 request")
    public static class UpdateImg {

        @NotNull
        @ApiModelProperty(value="프로필 이미지", required=true)
        private MultipartFile img;

    }

    @Getter
    @RequiredArgsConstructor
    @ApiModel(value = "프로필 유저네임/ 자기소개 변경 request")
    public static class UpdateContents {

        @NotBlank
        @ApiModelProperty(value="유저네임", required=true)
        private String username;

        @ApiModelProperty(value="자기 소개")
        private String introduction;

    }

}
