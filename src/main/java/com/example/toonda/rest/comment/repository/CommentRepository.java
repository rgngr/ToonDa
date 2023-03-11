package com.example.toonda.rest.comment.repository;

import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiary(Diary diary);

    @Query(value = "select c from Comment c where c.diary= :diary and c.deleted=false order by c.createdAt desc")
    List<Comment> getCommentList(@Param("diary") Diary diary);

    Optional<Comment> findByIdAndDeletedFalse(Long id);
}