package com.example.toonda.rest.comment.repository;

import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecommentRepository extends JpaRepository<Recomment, Long> {
    Long countByComment(Comment comment);

    Long countByCommentAndDeletedFalse(Comment comment);

    @Query(value = "select r from Recomment r where r.comment= :comment and r.deleted=false order by r.createdAt")
    List<Recomment> getRecommentList(@Param("comment")Comment comment);

    Optional<Recomment> findByIdAndDeletedFalse(Long id);

    @Query(value = "select r from Recomment r " +
            "where r.comment= :comment and r.id > :id and r.deleted=false and r.rrecommented=false ")
    Optional<Recomment> getLaterThanIdRecomment(@Param("comment")Comment comment, @Param("id")Long id);
}