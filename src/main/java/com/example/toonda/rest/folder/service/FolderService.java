package com.example.toonda.rest.folder.service;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.s3.S3Uploader;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.repository.BlockRepository;
import com.example.toonda.rest.comment.repository.CommentRepository;
import com.example.toonda.rest.diary.dto.DiaryResponseDto;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.dto.FolderRequestDto;
import com.example.toonda.rest.folder.dto.FolderResponseDto;
import com.example.toonda.rest.folder.dto.HashtagResponseDto;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.entity.Hashtag;
import com.example.toonda.rest.folder.repository.HashtagRepository;
import com.example.toonda.rest.folder.repository.FolderRepository;
import com.example.toonda.rest.like.repository.LikeRepository;
import com.example.toonda.rest.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final DiaryRepository diaryRepository;
    private final S3Uploader s3Uploader;
    private final HashtagRepository hashtagRepository;
    private final BlockRepository blockRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    // 폴더 생성
    @Transactional
    public FolderResponseDto.FolderId createFolder(FolderRequestDto requestDto) throws IOException {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 생성
        String folderImg = s3Uploader.upload(requestDto.getImg(), "file");
        Folder folder = folderRepository.save(new Folder(user, requestDto, folderImg));
        // 해시태그 저장
        if (requestDto.getHashtags().size()==1) {
            Hashtag h1 = new Hashtag(folder, requestDto.getHashtags().get(0));
            hashtagRepository.save(h1);
        } else if (requestDto.getHashtags().size()==2) {
            Hashtag h1 = new Hashtag(folder, requestDto.getHashtags().get(0));
            Hashtag h2 = new Hashtag(folder, requestDto.getHashtags().get(1));
            List<Hashtag> hashtags = Arrays.asList(h1, h2);
            hashtagRepository.saveAll(hashtags);
        } else if (requestDto.getHashtags().size()==3) {
            Hashtag h1 = new Hashtag(folder, requestDto.getHashtags().get(0));
            Hashtag h2 = new Hashtag(folder, requestDto.getHashtags().get(1));
            Hashtag h3 = new Hashtag(folder, requestDto.getHashtags().get(2));
            List<Hashtag> hashtags = Arrays.asList(h1, h2, h3);
            hashtagRepository.saveAll(hashtags);
        }

        return new FolderResponseDto.FolderId(folder);
    }

    // 폴더 상세 페이지
    @Transactional(readOnly = true)
    public FolderResponseDto getFolder(Long folderId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 존재 여부 확인 (deleted = false)
        Folder folder = folderRepository.findByIdAndDeletedFalse(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인
        boolean isBlocked = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked) throw new RestApiException(Code.NO_FOLDER);
        // 공개 여부 + 작성자 여부에 따라
        if (folder.isOpen() || (!folder.isOpen() && (user.getId() == folder.getUser().getId()))) {
            // 폴더 response 생성
            Long likeNum = likeRepository.countByFolder(folder);
            FolderResponseDto folderResponseDto = new FolderResponseDto(folder, likeNum);
            // 해시태그 붙이기
            List<String> hashtags = hashtagRepository.findAllByFolder(folder);
            for (String hashtag : hashtags) {
                folderResponseDto.addHashtag(new HashtagResponseDto(hashtag));
            }
            // 다이어리 붙이기 (deleted = false, order by date, createdAt)
            List<Diary> diaries = diaryRepository.findAllForFolderList(folder);
            for (Diary diary : diaries) {
                Long commentNum = commentRepository.countByDiaryAndDeletedFalse(diary);
                Long likeNum2 = likeRepository.countByDiary(diary);
                boolean isLike = likeRepository.existsByUserAndDiary(user, diary);
                folderResponseDto.addDiary(new DiaryResponseDto(user, diary, commentNum, likeNum2, isLike));
            }
            return folderResponseDto;
        } else {
         throw new RestApiException(Code.NO_FOLDER);
        }
    }

    // Get 폴더 수정 페이지
    @Transactional(readOnly = true)
    public FolderResponseDto getFolderUpdatePage(Long folderId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 존재 여부 확인 (deleted = false)
        Folder folder = folderRepository.findByIdAndDeletedFalse(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 본인 여부 확인
        if (user.getId() != folder.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        } else {
            // 폴더 response 생성
            FolderResponseDto folderResponseDto = new FolderResponseDto(folder);
            // 해시태그 붙이기
            List<String> hashtags = hashtagRepository.findAllByFolder(folder);
            for (String hashtag : hashtags) {
                folderResponseDto.addHashtag(new HashtagResponseDto(hashtag));
            }
            return folderResponseDto;
        }
    }

    // 폴더 수정
    @Transactional
    public void updateFolder(Long folderId, FolderRequestDto.Update requestDto) throws IOException {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 존재 여부 확인 (deleted = false)
        Folder folder = folderRepository.findByIdAndDeletedFalse(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 본인 여부 확인
        if (user.getId() != folder.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        } else {
            String currentImg = folder.getImg();
            String updateImg;
            // img 변경이 없을 경우
            if (requestDto.getImg()==null) {
                updateImg = currentImg;
            } else { // img 변경이 있는 경우
                s3Uploader.deleteFile(currentImg.split(".com/")[1]);
                updateImg = s3Uploader.upload(requestDto.getImg(), "file");
            }
            // 폴더 업데이트
            folder.updateFolder(requestDto, updateImg);
            // 기존 해시태그 삭제
            hashtagRepository.deleteByFolder(folder);
            // 새로운 해시태그 저장
            if (requestDto.getHashtags().size()==1) {
                Hashtag h1 = new Hashtag(folder, requestDto.getHashtags().get(0));
                hashtagRepository.save(h1);
            } else if (requestDto.getHashtags().size()==2) {
                Hashtag h1 = new Hashtag(folder, requestDto.getHashtags().get(0));
                Hashtag h2 = new Hashtag(folder, requestDto.getHashtags().get(1));
                List<Hashtag> hashtags = Arrays.asList(h1, h2);
                hashtagRepository.saveAll(hashtags);
            } else if (requestDto.getHashtags().size()==3) {
                Hashtag h1 = new Hashtag(folder, requestDto.getHashtags().get(0));
                Hashtag h2 = new Hashtag(folder, requestDto.getHashtags().get(1));
                Hashtag h3 = new Hashtag(folder, requestDto.getHashtags().get(2));
                List<Hashtag> hashtags = Arrays.asList(h1, h2, h3);
                hashtagRepository.saveAll(hashtags);
            }

        }
    }


    // 폴더 삭제
    @Transactional
    public void deleteFolder(Long folderId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 존재 여부 확인 (deleted = false)
        Folder folder = folderRepository.findByIdAndDeletedFalse(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 본인 여부 확인
        if (user.getId() != folder.getUser().getId()) {
            throw new RestApiException(Code.INVALID_USER);
        } else {
            // 이미지 s3 삭제
            String folderImg = folder.getImg();
            s3Uploader.deleteFile(folderImg.split(".com/")[1]);
            // 폴더 삭제 deleted = true
            folder.deleteFolder();
            // 해당 폴더의 모든 다이어리 deleted = true
            diaryRepository.deleteAllDiaries(folder);
            // 해당 다이어리의 이미지들도 s3에서 삭제해야 하는데.... img 테이블 따로 만들기? @scheduled 돌리기?
        }
    }

}
