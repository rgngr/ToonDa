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

    @Transactional
    public RecommentResponseDto createRecomment(Long commentId, RecommentRequestDto requestDto) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 댓글 존재 여부 확인
        Comment comment = commentRepository.getAliveComment(commentId).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 다이어리 존재 여부 확인
        boolean diaryExistence = diaryRepository.existsByIdAndDeletedFalse(comment.getDiary().getId());
        if (!diaryExistence) throw new RestApiException(Code.NO_DIARY);
        // 폴더 존재 여부 확인
        Folder folder = folderRepository.getAliveFolder(comment.getDiary().getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인
        boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
        if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        // 대댓글 생성
        Recomment recomment = recommentRepository.save(new Recomment(user, comment, requestDto));
        // 대댓글 작성 response 생성
        RecommentResponseDto recommentResponseDto = new RecommentResponseDto(recomment, false, 0L);
        return recommentResponseDto;
    }

    @Transactional(readOnly = true)
    public RecommentListResponseDto getRecomments(Long commentId) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 댓글 존재 여부 확인
        Comment comment = commentRepository.findByIdAndDeletedFalse(commentId).orElseThrow(() -> new RestApiException(Code.NO_COMMENT));
        // 다이어리 존재 여부 확인
        boolean diaryExistence = diaryRepository.existsByIdAndDeletedFalse(comment.getDiary().getId());
        if (!diaryExistence) throw new RestApiException(Code.NO_DIARY);
        // 폴더 존재 여부 확인
        Folder folder = folderRepository.getAliveFolder(comment.getDiary().getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인
        boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(comment.getUser(), user);
        if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
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

    @Transactional
    public void deleteRecomment(Long id) {
        // 유저 확인
        User user = SecurityUtil.getCurrentUser();
        if (user == null) throw new RestApiException(Code.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        // 대댓글 존재 여부 확인
        Recomment recomment = recommentRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new RestApiException(Code.NO_RECOMMENT));
        // 댓글 존재 여부 확인
        boolean commentExistence = commentRepository.existsByIdAndDeletedFalse(recomment.getComment().getId());
        if (!commentExistence) throw new RestApiException(Code.NO_COMMENT);
        // 다이어리 존재 여부 확인
        boolean diaryExistence = diaryRepository.existsByIdAndDeletedFalse(recomment.getComment().getDiary().getId());
        if (!diaryExistence) throw new RestApiException(Code.NO_DIARY);
        // 폴더 존재 여부 확인
        Folder folder = folderRepository.getAliveFolder(recomment.getComment().getDiary().getFolder().getId()).orElseThrow(() -> new RestApiException(Code.NO_FOLDER));
        // 차단 여부 확인
        boolean isBlocked1 = blockRepository.existsByUserAndBlockedUser(recomment.getComment().getUser(), user);
        if (isBlocked1) throw new RestApiException(Code.NO_COMMENT);
        boolean isBlocked2 = blockRepository.existsByUserAndBlockedUser(folder.getUser(), user);
        if (isBlocked2) throw new RestApiException(Code.NO_FOLDER);
        // 대댓글 삭제
        if (user.getId() == folder.getUser().getId()) {
            recomment.deleteRecomment();
        } else if (user.getId() == recomment.getUser().getId()) {
            if (recomment.isRrecommented()) throw new RestApiException(Code.NO_RECOMMENT);
            Recomment laterRecomment = recommentRepository.getLaterThanIdRecomment(recomment.getComment(), recomment.getId()).orElse(null);
            if (laterRecomment == null) {
                recomment.deleteRecomment();
            } else {
                recomment.updateRecomment();
            }
        } else {
            throw new RestApiException(Code.INVALID_USER);
        }
    }

}
