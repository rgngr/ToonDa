package com.example.toonda.rest.like.repository;

import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.entity.Recomment;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.folder.entity.Folder;
import com.example.toonda.rest.like.entity.Like;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndComment(User user, Comment comment);

    Long countByComment(Comment comment);

    boolean existsByUserAndRecomment(User user, Recomment recomment);

    Long countByRecomment(Recomment recomment);

    boolean existsByUserAndFolder(User user, Folder folder);

    Optional<Like> findByUserAndFolder(User user, Folder folder);

    Optional<Like> findByUserAndDiary(User user, Diary diary);

    Optional<Like> findByUserAndComment(User user, Comment comment);

    Optional<Like> findByUserAndRecomment(User user, Recomment recomment);

    Long countByFolder(Folder folder);
}