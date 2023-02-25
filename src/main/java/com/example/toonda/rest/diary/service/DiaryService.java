package com.example.toonda.rest.diary.service;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.s3.S3Uploader;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.diary.dto.DiaryRequestDto;
import com.example.toonda.rest.diary.dto.DiaryResponseDto;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.repository.FolderRepository;
import com.example.toonda.rest.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final FolderRepository folderRepository;
    private final DiaryRepository diaryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public DiaryResponseDto createDiary(Long folderId, DiaryRequestDto requestDto, MultipartFile img) throws IOException {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));

        String diaryImg = s3Uploader.upload(img, "file");
        Diary diary = diaryRepository.save(new Diary(user, folder, requestDto, diaryImg));

        return new DiaryResponseDto(user, diary);
    }

    @Transactional
    public DiaryResponseDto getDiaryUpdatePage(Long id) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new RestApiException(Code.NO_DIARY));

        if (user.getId() != diary.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        }

        return new DiaryResponseDto(user, diary);
    }

}
