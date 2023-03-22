package com.example.toonda.rest.like.controller;

import com.example.toonda.config.dto.ResponseDto;
import com.example.toonda.rest.like.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = {"5) 구독/좋아요"})
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "폴더 구독/취소")
    @PostMapping("/folders/{folderId}/like")
    public ResponseDto likeFolder(@PathVariable Long folderId) {
        return likeService.likeFolder(folderId);
    }

    @Operation(summary = "다이어리 좋아요/취소")
    @PostMapping("/diaries/{diaryId}/like")
    public ResponseDto likeDiary(@PathVariable Long diaryId) {
        return likeService.likeDiary(diaryId);
    }

    // 다이어리 좋아요 리스트

    @Operation(summary = "댓글 좋아요/취소")
    @PostMapping("/comments/{commentId}/like")
    public ResponseDto likeComment(@PathVariable Long commentId) {
        return likeService.likeComment(commentId);
    }

    @Operation(summary = "대댓글 좋아요/취소")
    @PostMapping("/recomments/{recommentId}/like")
    public ResponseDto likeRecomment(@PathVariable Long recommentId) {
        return likeService.likeRecomment(recommentId);
    }

}
