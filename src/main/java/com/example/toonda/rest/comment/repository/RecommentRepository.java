package com.example.toonda.rest.comment.repository;

import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {

    // 산제 안 된 대댓글 개수
    Long countByCommentAndDeletedFalse(Comment comment);

    // 대댓글 리스트
    @Query(value = "select r from Recomment r where r.comment= :comment and r.deleted=false")
    List<Recomment> getRecommentList(@Param("comment")Comment comment);

    // 특정 대댓글 이후의 대댓글, 삭제 안 된
    @Query(value = "select r from Recomment r where r.comment= :comment and r.id > :id and r.deleted=false and r.rrecommented=false ")
    Optional<Recomment> getLaterThanIdRecomment(@Param("comment")Comment comment, @Param("id")Long id);

    // 특정 대댓글, 삭제 안 된
    @Query(value = "select r from Recomment r where r.id= :id and r.deleted=false and r.rrecommented=false ")
    Optional<Recomment> getAliveRecomment(@Param("id")Long id);

    Optional<Recomment> findByIdAndDeletedFalse(Long id);
}