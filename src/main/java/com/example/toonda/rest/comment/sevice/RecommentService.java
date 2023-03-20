package com.example.toonda.rest.comment.sevice;

import com.example.toonda.config.exception.RestApiException;
import com.example.toonda.config.exception.errorcode.Code;
import com.example.toonda.config.security.SecurityUtil;
import com.example.toonda.rest.block.repository.BlockRepository;
import com.example.toonda.rest.comment.dto.RecommentListResponseDto;
import com.example.toonda.rest.comment.dto.RecommentRequestDto;
import com.example.toonda.rest.comment.dto.RecommentResponseDto;
import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.entity.Recomment;
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
public class RecommentService {

    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final FolderRepository folderRepository;
    private final BlockRepository blockRepository;
    private final RecommentRepository recommentRepository;
    private final LikeRepository likeRepository;

    // 대댓글 작성
    @Transactional
    public RecommentResponseDto createRecomment(Long commentId, RecommentRequestDto requestDto) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 댓글 존재 여부 확인 (deleted = false, recommented = false)
        Comment comment = commentRepository.getAliveComment(commentId).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        if (comment.getUser().getId() != user.getId()) {
            // 차단 여부 확인 (댓글)
            boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
            if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        }
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(comment.getDiary().getId()).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 차단 여부 확인 (다이어리)
        if (diary.getUser().getId() != user.getId()) {
            boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(diary.getUser(), user);
            if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        }
        // 본인 or 다른 사람 (폴더)
        if (diary.getUser().getId() == user.getId()) {
            // 폴더 존재 여부 확인 (deleted = false)
            Folder folder = folderRepository.findByIdAndDeletedFalse(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        } else {
            // 폴더 존재 여부 확인 (deleted = false, open = true)
            Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        }
        // 대댓글 생성
        Recomment recomment = recommentRepository.save(new Recomment(user, comment, requestDto));
        // 대댓글 작성 response 생성
        RecommentResponseDto recommentResponseDto = new RecommentResponseDto(recomment, false, 0L);
        return recommentResponseDto;
    }

    // 대댓글 리스트
    @Transactional(readOnly = true)
    public RecommentListResponseDto getRecomments(Long commentId) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 댓글 존재 여부 확인 (deleted = false)
        Comment comment = commentRepository.findByIdAndDeletedFalse(commentId).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 차단 여부 확인 (댓글)
        if (comment.getUser().getId() != user.getId()) {
            boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
            if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        }
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(comment.getDiary().getId()).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 차단 여부 확인 (다이어리)
        if (diary.getUser().getId() != user.getId()) {
            boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(diary.getUser(), user);
            if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        }
        // 본인 or 다른 사람 (폴더)
        if (diary.getUser().getId() == user.getId()) {
            // 폴더 존재 여부 확인 (deleted = false)
            Folder folder = folderRepository.findByIdAndDeletedFalse(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        } else {
            // 폴더 존재 여부 확인 (deleted = false, open = true)
            Folder folder = folderRepository.getAliveFolder(diary.getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        }
        // 대댓글 리스트 생성
        RecommentListResponseDto recommentListResponseDto = new RecommentListResponseDto();
        List<Recomment> recomments = recommentRepository.getRecommentList(comment);
        // null 이면 괜찮나......? 확인필요!!!
        for (Recomment recomment : recomments) {
            boolean isLike = likeRepository.existsByUserAndRecomment(user, recomment);
            Long likeNum = likeRepository.countByRecomment(recomment);
            recommentListResponseDto.addRecomment(new RecommentResponseDto(recomment, isLike, likeNum));
        }
        return recommentListResponseDto;
    }

    // 대댓글 삭제
    @Transactional
    public void deleteRecomment(Long id) {
        // 로그인 여부 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 대댓글 존재 여부 확인 (deleted = false)
        Recomment recomment = recommentRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new RestApiException(Code.NO_RECOMMENT));
        // 댓글 존재 여부 확인 (deleted = false)
        Comment comment = commentRepository.findByIdAndDeletedFalse(recomment.getComment().getId()).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 차단 여부 확인 (댓글)
        if (comment.getUser().getId() != user.getId()) {
            boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
            if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        }
        // 다이어리 존재 여부 확인 (deleted = false)
        Diary diary = diaryRepository.findByIdAndDeletedFalse(comment.getDiary().getId()).orElseThrow(() -> new RestApiException(Code.NO_DIARY));
        // 차단 여부 확인 (다이어리)
        if (diary.getUser().getId() != user.getId()) {
            boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(diary.getUser(), user);
            if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        }
        // 권한 없는 사람이 삭제
        if ((user.getId() != recomment.getUser().getId()) && (user.getId() != diary.getUser().getId())) {
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
        // 대댓글 삭제
        if (user.getId() == folder.getUser().getId()) {
            recomment.deleteRecomment(); // 다이어리 작성자가 삭제 >> deleted = true
        } else if ((user.getId() != folder.getUser().getId()) && (user.getId() == recomment.getUser().getId())) {
            if (recomment.isRrecommented()) throw new RestApiException(Code.NO_RECOMMENT); // rrecommented 처리 여부 확인
            Recomment laterRecomment = recommentRepository.getLaterThanIdRecomment(recomment.getComment(), recomment.getId()).orElse(null); // 더 이후의 대댓글 여부 확인
            if (laterRecomment == null) { // 더 이후의 대댓글이 없다면 deleted = true
                recomment.deleteRecomment();
            } else { // 더 이후의 대댓글 존재하면 rrecommented = true
                recomment.updateRecomment();
            }
        }
    }

}
