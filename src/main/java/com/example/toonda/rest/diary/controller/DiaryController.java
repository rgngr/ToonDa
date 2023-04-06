package com.example.toonda.rest.diary.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.diary.dto.DiaryRequestDto;
import com.example.toonda.rest.diary.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
@Api(tags = {"2) 다이어리"})
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "다이어리 생성")
    @PostMapping( "/{folderId}/diaries")
    public ResponseDto createDiary(@PathVariable Long folderId, @ModelAttribute @Valid DiaryRequestDto requestDto) throws IOException {
        return DataResponseDto.of(diaryService.createDiary(folderId, requestDto), Code.CREATE_DIARY.getStatusMsg());
    }

    @Operation(summary = "폴더 선택 리스트")
    @GetMapping("/diaries/folder-list")
    public ResponseDto getFolderList() {
        return DataResponseDto.of(diaryService.getFolderList(), Code.GET_FOLDERS.getStatusMsg());
    }

    @Operation(summary = "다이어리 수정 페이지")
    @GetMapping("/diaries/{diaryId}")
    public ResponseDto getDiaryUpdatePage(@PathVariable Long diaryId) {
        return DataResponseDto.of(diaryService.getDiaryUpdatePage(diaryId), Code.GET_DIARY_UPDATE_PAGE.getStatusMsg());
    }

    @Operation(summary = "다이어리 수정")
    @PatchMapping("/diaries/{diaryId}")
    public ResponseDto updateDiary(@PathVariable Long diaryId, @RequestBody @Valid DiaryRequestDto.Update requestDto) {
        diaryService.updateDiary(diaryId, requestDto);
        return ResponseDto.of(true, Code.UPDATE_DIARY);
    }

    @Operation(summary = "다이어리 삭제")
    @DeleteMapping("/diaries/{diaryId}")
    public ResponseDto deleteDiary(@PathVariable Long diaryId) {
        return DataResponseDto.of(diaryService.deleteDiary(diaryId), Code.DELETE_DIARY.getStatusMsg());
    }

    @Operation(summary = "다이어리 리스트")
    @GetMapping("/diaries")
    public ResponseDto getDiaries(
            @RequestParam(value = "folderId") Long folderId,
            @RequestParam(value="sortby", defaultValue = "asc", required = false) String sortby,
            @RequestParam(value = "page") int page) {
        return DataResponseDto.of(diaryService.getDiaries(folderId, sortby, page));
    }

}
