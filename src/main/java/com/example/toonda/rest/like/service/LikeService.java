package com.example.toonda.rest.like.service;

import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.repository.BlockRepository;
import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.entity.Recomment;
import com.example.toonda.rest.comment.repository.CommentRepository;
import com.example.toonda.rest.comment.repository.RecommentRepository;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.repository.FolderRepository;
import com.example.toonda.rest.like.entity.Like;
import com.example.toonda.rest.like.repository.LikeRepository;
import com.example.toonda.rest.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final FolderRepository folderRepository;
    private final BlockRepository blockRepository;
    private final LikeRepository likeRepository;
    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;

    // 폴더 구독/취소
    @Transactional
    public ResponseDto likeFolder(Long folderId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 폴더 존재 여부 확인 (deleted = false, open = true)
        Folder folder = folderRepository.getAliveFolder(folderId).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인
        boolean isBlocked = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked) throw new RestApiException(Code.NO_FOLDER);
        // 폴더 구독 여부 확인
        Like likeFolder = likeRepository.findByUserAndFolder(user, folder).orElse(null);
        boolean isLike;
        if (likeFolder == null) {
            likeFolder = likeRepository.save(createLike(user, folder, null, null, null));
            isLike = true;
        } else {
            likeRepository.delete(likeFolder);
            isLike = false;
        }
        return ResponseDto.of(isLike, Code.LIKE_FOLDER);
    }

    // 다이어리 좋아요/취소
    @Transactional
    public ResponseDto likeDiary(Long diaryId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(diaryId).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 폴더 존재 여부 확인 (deleted = false, open = true)
        Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인
        boolean isBlocked = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked) throw new RestApiException(Code.NO_FOLDER);
        // 다이어리 좋아요 여부 확인
        Like likeDiary = likeRepository.findByUserAndDiary(user, diary).orElse(null);
        boolean isLike;
        if (likeDiary == null) {
            likeDiary = likeRepository.save(createLike(user,null,diary,null,null));
            isLike = true;
        } else {
            likeRepository.delete(likeDiary);
            isLike = false;
        }
        return ResponseDto.of(isLike, Code.LIKE_DIARY);
    }

    // 댓글 좋아요/취소
    @Transactional
    public ResponseDto likeComment(Long commentId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 댓글 존재 여부 확인 (deleted = false, recommented = false)
        Comment comment = commentRepository.getAliveComment(commentId).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(comment.getDiary().getId()).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 폴더 존재 여부 확인 (deleted = false, open = true)
        Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인 (댓글, 폴더)
        boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
        if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        // 댓글 좋아요 여부 확인
        Like likeComment = likeRepository.findByUserAndComment(user, comment).orElse(null);
        boolean isLike;
        if (likeComment == null) {
            likeComment = likeRepository.save(createLike(user, null, null, comment, null));
            isLike = true;
        } else {
            likeRepository.delete(likeComment);
            isLike = false;
        }
        return ResponseDto.of(isLike, Code.LIKE_COMMENT);
    }

    // 대댓글 좋아요/취소
    @Transactional
    public ResponseDto likeRecomment(Long recommentId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 대댓글 존재 여부 확인 (deleted = false, rrecommented = false)
        Recomment recomment = recommentRepository.getAliveRecomment(recommentId).orElseThrow(() -> new RestApiException(Code.NO_RECOMMENT));
        // 댓글 존재 여부 확인 (deleted = false, recommented = false)
        Comment comment = commentRepository.getAliveComment(recomment.getComment().getId()).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(comment.getDiary().getId()).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 폴더 존재 여부 확인 (deleted = false, open = true)
        Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인 (댓글, 폴더)
        boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
        if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        // 대댓글 좋아요 여부 확인
        Like likeRecomment = likeRepository.findByUserAndRecomment(user, recomment).orElse(null);
        boolean isLike;
        if (likeRecomment == null) {
            likeRecomment = likeRepository.save(createLike(user, null, null, null, recomment));
            isLike = true;
        } else {
            likeRepository.delete(likeRecomment);
            isLike = false;
        }
        return ResponseDto.of(isLike, Code.LIKE_RECOMMENT);
    }

    // Like 생성
    private Like createLike(User user, Folder folder, Diary diary, Comment comment, Recomment recomment) {
        return Like.builder()
                .user(user)
                .folder(folder)
                .diary(diary)
                .comment(comment)
                .recomment(recomment)
                .build();
    }

}
