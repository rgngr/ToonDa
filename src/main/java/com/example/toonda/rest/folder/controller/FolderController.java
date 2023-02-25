package com.example.toonda.rest.folder.controller;

import com.example.toonda.config.dto.DataResponseDto;
import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.folder.service.FolderService;
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
@Tag(name = "folders", description = "폴더 관련 API")
public class FolderController {

    private final FolderService folderService;

    @ApiOperation(value = "폴더 생성")
    @PostMapping(value = "", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto createFolder(@RequestBody @Valid FolderRequestDto requestDto,
                                    @RequestPart(value = "file") MultipartFile img) throws IOException {
        return DataResponseDto.of( folderService.createFolder(requestDto, img), Code.CREATE_FOLDER.getStatusMsg());
    }

    @ApiOperation(value = "폴더 불러오기")
    @GetMapping("/{id}")
    public ResponseDto getFolder(@PathVariable Long id) {
        return DataResponseDto.of( folderService.getFolder(id), Code.CREATE_FOLDER.getStatusMsg());
    }

}
