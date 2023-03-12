package com.example.toonda.rest.folder.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.folder.service.FolderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
@Tag(name = "folders", description = "폴더 관련 API")
public class FolderController {

    private final FolderService folderService;

    @ApiOperation("폴더 생성")
    @PostMapping("")
    public ResponseDto createFolder(@ModelAttribute @Valid FolderRequestDto requestDto) throws IOException {
        return DataResponseDto.of(folderService.createFolder(requestDto), Code.CREATE_FOLDER.getStatusMsg());
    }

    @ApiOperation("폴더 불러오기")
    @GetMapping("/{id}")
    public ResponseDto getFolder(@PathVariable Long id) {
        return DataResponseDto.of(folderService.getFolder(id), Code.CREATE_FOLDER.getStatusMsg());
    }

    @ApiOperation("GET 폴더 수정 페이지")
    @GetMapping("/{id}/update-page")
    public ResponseDto getFolderUpdatePage(@PathVariable Long id) {
        return DataResponseDto.of(folderService.getFolderUpdatePage(id), Code.GET_FOLDER_UPDATE_PAGE.getStatusMsg());
    }

    @ApiOperation("폴더 수정")
    @PatchMapping("/{id}")
    public ResponseDto updateFolder(@PathVariable Long id, @ModelAttribute @Valid FolderRequestDto.Update requestDto) throws IOException {
        folderService.updateFolder(id, requestDto);
        return ResponseDto.of(true, Code.UPDATE_FOLDER);
    }

    @ApiOperation("폴더 삭제")
    @DeleteMapping("/{id}")
    public ResponseDto deleteFolder(@PathVariable Long id) {
        folderService.deleteFolder(id);
        return ResponseDto.of(true, Code.DELETE_FOLDER);
    }

}
