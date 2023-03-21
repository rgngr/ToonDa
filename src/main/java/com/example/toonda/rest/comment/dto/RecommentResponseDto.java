package com.example.toonda.rest.comment.dto;

import com.example.toonda.rest.comment.entity.Recomment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class RecommentResponseDto {

    private Long recommentId;
    private Long userId;
    private String userImg;
    private String username;
    private String recomment;
    private boolean isLike;
    private boolean isRrecommented;
    private Long likeNum;
    private LocalDateTime createdAt;

    public RecommentResponseDto(Recomment recomment, boolean isLike, Long likeNum) {
        this.recommentId = recomment.getId();
        this.userId = recomment.getUser().getId();
        this.userImg = recomment.getUser().getImg();
        this.username = recomment.getUser().getUsername();
        this.recomment = recomment.getRecomment();
        this.isLike = isLike;
        this.isRrecommented = recomment.isRrecommented();
        this.likeNum = likeNum;
        this.createdAt = recomment.getCreatedAt();
    }

}
