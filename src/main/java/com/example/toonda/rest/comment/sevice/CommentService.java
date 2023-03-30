package com.example.toonda.rest.comment.sevice;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.repository.BlockRepository;
import com.example.toonda.rest.comment.dto.CommentListResponseDto;
import com.example.toonda.rest.comment.dto.CommentRequestDto;
import com.example.toonda.rest.comment.dto.CommentResponseDto;
import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.repository.CommentRepository;
import com.example.toonda.rest.comment.repository.RecommentRepository;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.diary.repository.DiaryRepository;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.folder.repository.FolderRepository;
import com.example.toonda.rest.like.repository.LikeRepository;
import com.example.toonda.rest.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final LikeRepository likeRepository;
    private final RecommentRepository recommentRepository;
    private final BlockRepository blockRepository;
    private final FolderRepository folderRepository;

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long diaryId, CommentRequestDto requestDto) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(diaryId).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 차단 여부 확인 (다이어리)
        if (diary.getUser().getId() != user.getId()) {
            boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(diary.getUser(), user);
            if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        }
        // 본인 or 다른 사람
        if (diary.getUser().getId() == user.getId()) {
            // 폴더 존재 여부 확인 (deleted = false)
            Folder folder = folderRepository.findByIdAndDeletedFalse(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        } else {
            // 폴더 존재 여부 확인 (deleted = false, open = true)
            Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        }
        // 댓글 생성
        Comment comment = commentRepository.save(new Comment(user, diary, requestDto));
        // 댓글 작성 response 생성
        CommentResponseDto commentResponseDto = createCommentResponse(comment, false, 0L, 0L);
        return commentResponseDto;
    }

    // 댓글 리스트
    @Transactional(readOnly = true)
    public CommentListResponseDto getComments(Long diaryId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(diaryId).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 차단 여부 확인 (다이어리)
        if (diary.getUser().getId() != user.getId()) {
            boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(diary.getUser(), user);
            if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        }
        // 본인 or 다른 사람
        if (diary.getUser().getId() == user.getId()) {
            // 폴더 존재 여부 확인 (deleted = false)
            Folder folder = folderRepository.findByIdAndDeletedFalse(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        } else {
            // 폴더 존재 여부 확인 (deleted = false, open = true)
            Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        }
        // 댓글 리스트 생성
        CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
        List<Comment> comments = commentRepository.getCommentList(diary, user);
        // null 이면 괜찮나......? 확인필요!!!
        for (Comment comment : comments) {
            boolean isLike = likeRepository.existsByUserAndComment(user, comment);
            Long likeNum = likeRepository.countByComment(comment);
            Long recommentNum = recommentRepository.countByCommentAndDeletedFalse(comment);
            commentListResponseDto.addComment(createCommentResponse(comment, isLike, likeNum, recommentNum));
        }
        return commentListResponseDto;
    }

    // 댓글 삭제
    @Transactional
    public CommentResponseDto.Delete deleteComment(Long id) {
        // 로그인 유무 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 댓글 존재 여부 확인 (deleted = false)
        Comment comment = commentRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(comment.getDiary().getId()).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 차단 여부 확인 (다이어리)
        if (diary.getUser().getId() != user.getId()) {
            boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(diary.getUser(), user);
            if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        }
        // 권한 없는 사람이 삭제
        if ((user.getId() != comment.getUser().getId()) && (user.getId() != diary.getUser().getId())) {
            throw new RestApiException(Code.INVALID_USER);
        }
        // 본인 or 다른 사람 (폴더)
        Folder folder;
        if (diary.getUser().getId() == user.getId()) {
            // 폴더 존재 여부 확인 (deleted = false)
            folder = folderRepository.findByIdAndDeletedFalse(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        } else {
            // 폴더 존재 여부 확인 (deleted = false, open = true)
            folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        }
        // 댓글 삭제 (본인 or 다이어리 작성자)
        if (user.getId() == folder.getUser().getId()) { // 다이어리 작성자가 삭제 >> deleted = true
            comment.deleteComment();
        } else if ((user.getId() != folder.getUser().getId()) && (user.getId() == comment.getUser().getId())) {
            if (comment.isRecommented()) throw new RestApiException(Code.NO_COMMENT); // recommented 처리 여부 확인
            Long recommentNum = recommentRepository.countByCommentAndDeletedFalse(comment); // 대댓글 개수
            if (recommentNum == 0) { // 댓글 작성자 + 대댓글 없음 >> deleted = true
                comment.deleteComment();
            } else { // 댓글 작성자 + 대댓글 존재 >> recommented = true
                comment.updateComment();
            }
        }
        return new CommentResponseDto.Delete(comment);
    }

    // CommentResponseDto 생성
    private CommentResponseDto createCommentResponse(Comment comment, boolean isLike, Long likeNum, Long recommentNum) {
        return CommentResponseDto.builder()
                                 .comment(comment)
                                 .isLike(isLike)
                                 .likeNum(likeNum)
                                 .recommentNum(recommentNum)
                                 .build();
    }

}
