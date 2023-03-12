package com.example.toonda.rest.like.repository;

import com.example.toonda.rest.comment.entity.Comment;
import com.example.toonda.rest.comment.entity.Recomment;
import com.example.toonda.rest.like.entity.Like;
import com.example.toonda.rest.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndComment(User user, Comment comment);

    Long countByComment(Comment comment);

    boolean existsByUserAndRecomment(User user, Recomment recomment);

    Long countByRecomment(Recomment recomment);
}