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
    @PostMapping(value = "/{folderId}/diaries", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto createDiary(@PathVariable Long folderId, @RequestBody @Valid DiaryRequestDto requestDto,
                                   @RequestPart(value = "file") MultipartFile img) throws IOException {
        return DataResponseDto.of(diaryService.createDiary(folderId, requestDto, img), Code.CREATE_DIARY.getStatusMsg());
    }

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

}
