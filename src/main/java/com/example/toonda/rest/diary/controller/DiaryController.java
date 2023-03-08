package com.example.toonda.rest.diary.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.diary.dto.DiaryRequestDto;
import com.example.toonda.rest.diary.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
@Tag(name = "diaries", description = "다이어리 관련 API")
public class DiaryController {

    private final DiaryService diaryService;

    @ApiOperation(value = "다이어리 생성")
    @PostMapping(value = "/{folderId}/diaries")
    public ResponseDto createDiary(@PathVariable Long folderId, @ModelAttribute @Valid DiaryRequestDto requestDto) throws IOException {
        return DataResponseDto.of(diaryService.createDiary(folderId, requestDto), Code.CREATE_DIARY.getStatusMsg());
    }

//    @ApiOperation(value = "폴더 선택")
//    @GetMapping("/diaries/select-folder")
//    public ResponseDto getFolders() {
//        return DataResponseDto.of(diaryService.getFolders(), Code.GET_FOLDER_LIST.getStatusMsg());
//    }

    @ApiOperation(value = "GET 다이어리 수정 페이지")
    @GetMapping("/diaries/{id}")
    public ResponseDto getDiaryUpdatePage(@PathVariable Long id) {
        return DataResponseDto.of(diaryService.getDiaryUpdatePage(id), Code.GET_DIARY_UPDATE_PAGE.getStatusMsg());
    }

    @ApiOperation(value = "다이어리 수정")
    @PatchMapping("/diaries/{id}")
    public ResponseDto updateDiary(@PathVariable Long id, @RequestBody @Valid DiaryRequestDto.Update requestDto) {
        diaryService.updateDiary(id, requestDto);
        return ResponseDto.of(true, Code.UPDATE_DIARY);
    }

    @ApiOperation(value = "다이어리 삭제")
    @DeleteMapping("/diaries/{id}")
    public ResponseDto deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
        return ResponseDto.of(true, Code.DELETE_DIARY);
    }

}
