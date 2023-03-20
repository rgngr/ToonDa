package com.example.toonda.rest.user.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.user.dto.MypageRequestDto;
import com.example.toonda.rest.user.service.MypageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Tag(name = "mypage", description = "프로필, 폴더 리스트, 프로필 수정 (페이지), 다이어리 좋아요 리스트")
public class MypageController {

    private final MypageService mypageService;

    @ApiOperation("get 프로필 (img, username, introduction)")
    @GetMapping("/{userId}/profile")
    public ResponseDto getProfile(@PathVariable Long userId) {
        return DataResponseDto.of(mypageService.getProfile(userId), Code.GET_PROFILE.getStatusMsg());
    }

    @ApiOperation("get 폴더 리스트")
    @GetMapping("/{userId}/folders")
    public ResponseDto getFolders(@PathVariable Long userId) {
        return DataResponseDto.of(mypageService.getFolders(userId), Code.GET_FOLDERS.getStatusMsg());
    }

    @ApiOperation("get 프로필 수정 페이지")
    @GetMapping("/profile/updqte-page")
    public ResponseDto getUpdatePage() {
        return DataResponseDto.of(mypageService.getUpdatePage(), Code.GET_PROFILE_UPDATE_PAGE.getStatusMsg());
    }

    @ApiOperation("프로필 이미지 변경")
    @PatchMapping("/profile/img")
    public ResponseDto updateProfileImg(@ModelAttribute @Valid MypageRequestDto.UpdateImg requestDto) throws IOException {
        mypageService.updateProfileImg(requestDto);
        return ResponseDto.of(true, Code.UPDATE_PROFILE_IMG);
    }

    @ApiOperation("프로필 이미지 삭제")
    @DeleteMapping("/profile/img")
    public ResponseDto deleteProfileImg() {
        mypageService.deleteProfileImg();
        return ResponseDto.of(true, Code.DELETE_PROFILE_IMG);
    }

    @ApiOperation("username/ introduction 수정")
    @PatchMapping("/profile/contents")
    public ResponseDto updateProfileContents(@RequestBody @Valid MypageRequestDto.UpdateContents requestDto) {
        mypageService.updateProfileContents(requestDto);
        return ResponseDto.of(true, Code.UPDATE_PROFILE_CONTENTS);
    }

    @ApiOperation("다이어리 좋아요 리스트")
    @GetMapping("/like-diaries")
    public ResponseDto getLikeDiaries() {
        return DataResponseDto.of(mypageService.getLikeDiaries(), Code.GET_LIKE_DIARIES.getStatusMsg());
    }

    // 휴지통

}
