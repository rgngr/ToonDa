package com.example.toonda.rest.diary.service;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.s3.S3Uploader;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.diary.dto.DiaryRequestDto;
import com.example.toonda.rest.diary.dto.DiaryResponseDto;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.dto.FolderResponseDto;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.repository.FolderRepository;
import com.example.toonda.rest.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final FolderRepository folderRepository;
    private final DiaryRepository diaryRepository;
    private final S3Uploader s3Uploader;

    // 다이어리 생성
    @Transactional
    public DiaryResponseDto.Shorten createDiary(Long folderId, DiaryRequestDto requestDto) throws IOException {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 존재 여부 확인 (deleted = false)
        Folder folder = folderRepository.findByIdAndDeletedFalse(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 폴더 작성자 본인 여부 확인
        if (user.getId() != folder.getUser().getId()) throw new RestApiException(Code.INVALID_USER);
        // 다이어리 생성
        String diaryImg = s3Uploader.upload(requestDto.getImg(), "file");
        Diary diary = diaryRepository.save(new Diary(user, folder, requestDto, diaryImg));
        // diaryNum++
        folder.plusDiaryNum();

        return new DiaryResponseDto.Shorten(user, diary);
    }

    // 폴더 선택 리스트
    @Transactional(readOnly = true)
    public DiaryResponseDto.FolderList getFolderList() {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 리스트 생성 (delete = false)
        DiaryResponseDto.FolderList folderList = new DiaryResponseDto.FolderList();
        List<Folder> folders = folderRepository.findAllUsersFolders(user);
        for (Folder folder : folders) {
            FolderResponseDto.Title title = new FolderResponseDto.Title(folder);
            folderList.addFolder(title);
        }
        return folderList;
    }

    // get 다이어리 수정 페이지
    @Transactional(readOnly = true)
    public DiaryResponseDto.Shorten getDiaryUpdatePage(Long diaryId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 다이어리 작성자 여부 확인
        if (user.getId() != diary.getUser().getId()) throw new RestApiException(Code.INVALID_USER);
        // 다이어리 수정 페이지 생성
        return new DiaryResponseDto.Shorten(user, diary);
    }

    // 다이어리 수정
    @Transactional
    public void updateDiary(Long diaryId, DiaryRequestDto.Update requestDto) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 다이어리 작성자 여부 확인
        if (user.getId() != diary.getUser().getId()) throw new RestApiException(Code.INVALID_USER);
        // 다이어리 수정
        diary.updateDiary(requestDto);
    }

    // 다이어리 삭제
    @Transactional
    public void deleteDiary(Long diaryId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 다이어리 작성자 여부 확인
        if (user.getId() != diary.getUser().getId()) throw new RestApiException(Code.INVALID_USER);
        // 다이어리 삭제
        String diaryImg = diary.getImg();
        s3Uploader.deleteFile(diaryImg.split(".com/")[1]);
        diary.deleteDiary();
    }

    // 다이어리 리스트

}
