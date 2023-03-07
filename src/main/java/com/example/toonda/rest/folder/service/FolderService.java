package com.example.toonda.rest.folder.service;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.s3.S3Uploader;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.diary.dto.DiaryResponseDto;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
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
public class FolderService {

    private final FolderRepository folderRepository;
    private final DiaryRepository diaryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public FolderResponseDto createFolder(FolderRequestDto requestDto) throws IOException {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        String folderImg = s3Uploader.upload(requestDto.getImg(), "file");
        Folder folder = folderRepository.save(new Folder(user, requestDto, folderImg));

        return new FolderResponseDto(folder);

    }

    @Transactional(readOnly = true)
    public FolderResponseDto getFolder(Long id) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        Folder folder = folderRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        if (folder.isOpen() || (!folder.isOpen() && (user.getId() == folder.getUser().getId()))) {
            FolderResponseDto folderResponseDto = new FolderResponseDto(folder);
            List<Diary> diaries = diaryRepository.findByFolderAndDeletedIsFalse(folder);
            if (diaries == null) {
                return folderResponseDto;
            }
            for (Diary diary : diaries) {
                folderResponseDto.addDiary(new DiaryResponseDto(user,diary));
            }
            return folderResponseDto;
        } else {
         throw new RestApiException(Code.NO_FOLDER);
        }
    }

    @Transactional(readOnly = true)
    public FolderResponseDto getFolderUpdatePage(Long id) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        Folder folder = folderRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));

        if (user.getId() != folder.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        } else {
            return (new FolderResponseDto(folder));
        }
    }

    @Transactional
    public void updateFolder(Long id, FolderRequestDto.Update requestDto) throws IOException {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        Folder folder = folderRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));

        if (user.getId() != folder.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        } else {
            String currentImg = folder.getImg();
            String updateImg;

            if (requestDto.getImg().isEmpty()) {
                updateImg = currentImg;
            } else {
                updateImg = s3Uploader.upload(requestDto.getImg(), "file");
            }

            folder.updateFolder(requestDto, updateImg);
        }
    }

    @Transactional
    public void deleteFolder(Long id) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);

        Folder folder = folderRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));

        if (user.getId() != folder.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        } else {

            String folderImg = folder.getImg();
            s3Uploader.deleteFile(folderImg.split(".com/")[1]);

            List<Diary> diaries = diaryRepository.findByFolder(folder);
            if (diaries == null) {
                folder.deleteFolder();
            } else {
                folder.deleteFolder();
                for (Diary diary : diaries) {
                    diary.deleteDiary();
                }
            }
        }
    }

}
