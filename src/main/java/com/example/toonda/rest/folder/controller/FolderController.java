package com.example.toonda.rest.folder.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.folder.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folders")
@Api(tags = {"1) 폴더"})
public class FolderController {

    private final FolderService folderService;

    @Operation(summary = "폴더 생성")
    @PostMapping("")
    public ResponseDto createFolder(@ModelAttribute @Valid FolderRequestDto requestDto) throws IOException {
        return DataResponseDto.of(folderService.createFolder(requestDto), Code.CREATE_FOLDER.getStatusMsg());
    }

    @Operation(summary = "폴더 상세 페이지")
    @GetMapping("/{folderId}")
    public ResponseDto getFolder(@PathVariable Long folderId) {
        return DataResponseDto.of(folderService.getFolder(folderId), Code.GET_FOLDER.getStatusMsg());
    }

    @Operation(summary = "폴더 수정 페이지")
    @GetMapping("/{folderId}/update-page")
    public ResponseDto getFolderUpdatePage(@PathVariable Long folderId) {
        return DataResponseDto.of(folderService.getFolderUpdatePage(folderId), Code.GET_FOLDER_UPDATE_PAGE.getStatusMsg());
    }

    @Operation(summary = "폴더 수정")
    @PatchMapping("/{folderId}")
    public ResponseDto updateFolder(@PathVariable Long folderId, @ModelAttribute @Valid FolderRequestDto.Update requestDto) throws IOException {
        folderService.updateFolder(folderId, requestDto);
        return ResponseDto.of(true, Code.UPDATE_FOLDER);
    }

    @Operation(summary = "폴더 삭제")
    @DeleteMapping("/{folderId}")
    public ResponseDto deleteFolder(@PathVariable Long folderId) {
        return DataResponseDto.of(folderService.deleteFolder(folderId), Code.DELETE_FOLDER.getStatusMsg());
    }

}
