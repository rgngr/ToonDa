package com.example.toonda.rest.comment.dto;

import com.example.toonda.rest.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Schema(description = "댓글 response")
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

}
