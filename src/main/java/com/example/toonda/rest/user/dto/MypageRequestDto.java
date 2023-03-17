package com.example.toonda.rest.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Schema(description = "마이페이지 request")
public class MypageRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "프로필 이미지 변경 request")
    public static class UpdateImg {

        @Schema (description = "이미지")
        @NotNull
        private MultipartFile img;

    }

    @Getter
    @NoArgsConstructor
    @Schema(description = "프로필 username/ introduction request")
    public static class UpdateContents {

        @Schema (description = "유저네임")
        @NotBlank
        private String username;

        @Schema (description = "자기소개")
        private String introduction;

    }

}
