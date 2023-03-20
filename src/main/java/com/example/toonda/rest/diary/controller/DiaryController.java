package com.example.toonda.rest.diary.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.diary.dto.DiaryRequestDto;
import com.example.toonda.rest.diary.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
@Tag(name = "diaries", description = "다이어리 생성/ get 수정 페이지/ 수정/ 삭제, 폴더 선택, 다이어리 리스트")
public class DiaryController {

    private final DiaryService diaryService;

    @ApiOperation("다이어리 생성")
    @PostMapping( "/{folderId}/diaries")
    public ResponseDto createDiary(@PathVariable Long folderId, @ModelAttribute @Valid DiaryRequestDto requestDto) throws IOException {
        return DataResponseDto.of(diaryService.createDiary(folderId, requestDto), Code.CREATE_DIARY.getStatusMsg());
    }

    @ApiOperation("폴더 선택 리스트")
    @GetMapping("/diaries/folder-list")
    public ResponseDto getFolderList() {
        return DataResponseDto.of(diaryService.getFolderList(), Code.GET_FOLDERS.getStatusMsg());
    }

    @ApiOperation("GET 다이어리 수정 페이지")
    @GetMapping("/diaries/{diaryId}")
    public ResponseDto getDiaryUpdatePage(@PathVariable Long diaryId) {
        return DataResponseDto.of(diaryService.getDiaryUpdatePage(diaryId), Code.GET_DIARY_UPDATE_PAGE.getStatusMsg());
    }

    @ApiOperation("다이어리 수정")
    @PatchMapping("/diaries/{diaryId}")
    public ResponseDto updateDiary(@PathVariable Long diaryId, @RequestBody @Valid DiaryRequestDto.Update requestDto) {
        diaryService.updateDiary(diaryId, requestDto);
        return ResponseDto.of(true, Code.UPDATE_DIARY);
    }

    @ApiOperation("다이어리 삭제")
    @DeleteMapping("/diaries/{diaryId}")
    public ResponseDto deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseDto.of(true, Code.DELETE_DIARY);
    }

//    @ApiOperation("다이어리 리스트")
//    @GetMapping("/{folderId}/diary-list")
//    public ResponseDto getDiaryList(@PathVariable Long folderId) {
//        return DataResponseDto.of(diaryService.getDiaryList(folderId), Code.GET_DIARIES.getStatusMsg());
//    }

}
