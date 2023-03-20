package com.example.toonda.rest.user.service;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.s3.S3Uploader;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.repository.BlockRepository;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.repository.FolderRepository;
import com.example.toonda.rest.like.repository.LikeRepository;
import com.example.toonda.rest.user.dto.DiaryListResponseDto;
import com.example.toonda.rest.user.dto.FolderListResponseDto;
import com.example.toonda.rest.user.dto.MypageRequestDto;
import com.example.toonda.rest.user.dto.ProfileResponseDto;
import com.example.toonda.rest.user.entity.User;
import com.example.toonda.rest.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final FolderRepository folderRepository;
    private final LikeRepository likeRepository;
    private final S3Uploader s3Uploader;
    private final DiaryRepository diaryRepository;

    // get 프로필
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(Long userId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 본인 or 다른 사람
        if (user.getId() == userId) {
            // 프로필 생성
            return new ProfileResponseDto(user);
        } else {
            // 유저 존재 여부 확인 (deleted = false)
            User oppUser = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() -> new RestApiException(Code.NO_USER));
            // 차단 여부 확인
            boolean isBlocked = blockRepository.existsByUserAndBlockedUser(oppUser, user);
            if (isBlocked) throw new RestApiException(Code.NO_USER);
            // oppUser 프로필 생성
            return new ProfileResponseDto(oppUser);
        }
    }

    // get 폴더 리스트
    @Transactional(readOnly = true)
    public FolderListResponseDto getFolders(Long userId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 본인 or 다른 사람
        if (user.getId() == userId) {
            // 폴더 리스트 생성 (deleted = false, order by modifiedAt desc)
            FolderListResponseDto folderListResponseDto = new FolderListResponseDto();
            List<Folder> folders = folderRepository.findAllUsersFolders(user);
            for (Folder folder : folders) {
                Long likeNum = likeRepository.countByFolder(folder);
                FolderListResponseDto.Folders foldersFolder = new FolderListResponseDto.Folders(folder, likeNum);
                folderListResponseDto.addFolder(foldersFolder);
            }
            return folderListResponseDto;
        } else {
            // 유저 존재 여부 확인 (deleted = false)
            User oppUser = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() -> new RestApiException(Code.NO_USER));
            // 차단 여부 확인
            boolean isBlocked = blockRepository.existsByUserAndBlockedUser(oppUser, user);
            if (isBlocked) throw new RestApiException(Code.NO_USER);
            // 폴더 리스트 생성 (deleted = false, open = true)
            FolderListResponseDto folderListResponseDto = new FolderListResponseDto();
            List<Folder> folders = folderRepository.findAllForMypageFolderList(oppUser);
            for (Folder folder : folders) {
                Long likeNum = likeRepository.countByFolder(folder);
                FolderListResponseDto.Folders foldersFolder = new FolderListResponseDto.Folders(folder, likeNum);
                folderListResponseDto.addFolder(foldersFolder);
            }
            return folderListResponseDto;
        }
    }

    // get 프로필 수정 페이지
    @Transactional(readOnly = true)
    public ProfileResponseDto getUpdatePage() {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 프로필 수정 페이지 생성
        return new ProfileResponseDto(user);
    }

    // 프로필 이미지 변경
    @Transactional
    public void updateProfileImg(MypageRequestDto.UpdateImg requestDto) throws IOException {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 이미지 변경
        String currentImg = user.getImg();
        s3Uploader.deleteFile(currentImg.split(".com/")[1]);
        String updateImg = s3Uploader.upload(requestDto.getImg(), "file");
        user.updateImg(updateImg);
        userRepository.save(user);
    }

    // 프로필 이미지 삭제
    @Transactional
    public void deleteProfileImg() {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 이미지 삭제
        String currentImg = user.getImg();
        s3Uploader.deleteFile(currentImg.split(".com/")[1]);
        user.deleteImg();
        userRepository.save(user);
    }

    // username/ introduction 변경
    @Transactional
    public void updateProfileContents(MypageRequestDto.UpdateContents requestDto) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // username/ introduction 변경
        String username = requestDto.getUsername();
        String introduction = requestDto.getIntroduction();
        user.updateContents(username, introduction);
        userRepository.save(user);
    }

    // 다이어리 좋아요 리스트
    @Transactional(readOnly = true)
    public DiaryListResponseDto getLikeDiaries() {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 좋아요 리스트 생성
        DiaryListResponseDto diaryListResponseDto = new DiaryListResponseDto();
        List<Diary> diaries = diaryRepository.findAllForDiaryList(user);
        for (Diary diary : diaries) {
            DiaryListResponseDto.Diaries diariesDiary = new DiaryListResponseDto.Diaries(diary);
            diaryListResponseDto.addDiary(diariesDiary);
        }
        return diaryListResponseDto;
    }

}
