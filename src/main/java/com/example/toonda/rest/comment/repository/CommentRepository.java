package com.example.toonda.rest.comment.repository;

import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDiary(Diary diary);

    @Query(value = "select c from Comment c " +
            "where c.diary= :diary and c.deleted=false and c.user not in(select b.user from Block b where b.blockedUser= :user)")
    List<Comment> getCommentList(@Param("diary") Diary diary, @Param("user")User user);

    Optional<Comment> findByIdAndDeletedFalse(Long id);

    @Query(value = "select c from Comment c where c.id= :id and c.deleted = false and c.recommented = false")
    Optional<Comment> getAliveComment(@Param("id") Long id);

    boolean existsByIdAndDeletedFalse(Long id);
}