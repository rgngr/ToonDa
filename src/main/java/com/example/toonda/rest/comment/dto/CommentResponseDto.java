package com.example.toonda.rest.comment.dto;

import com.example.toonda.rest.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private Long userId;
    private String userImg;
    private String username;
    private String comment;
    private boolean isLike;
    private boolean isRecommented;
    private Long likeNum;
    private Long recommentNum;
    private LocalDateTime createdAt;

    @Builder
    public CommentResponseDto(Comment comment, boolean isLike, Long likeNum, Long recommentNum) {
        this.commentId = comment.getId();
        this.userId = comment.getUser().getId();
        this.userImg = comment.getUser().getImg();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.isLike = isLike;
        this.isRecommented = comment.isRecommented();
        this.likeNum = likeNum;
        this.recommentNum = recommentNum;
        this.createdAt = comment.getCreatedAt();
    }

    @Getter
    public static class Delete {

        private Long commentId;
        private boolean recommented;
        private boolean deleted;

        public Delete(Comment comment) {
            this.commentId = comment.getId();
            this.recommented = comment.isRecommented();
            this.deleted = comment.isDeleted();
        }
    }

}
