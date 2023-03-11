package com.example.toonda.rest.comment.entity;

import com.example.toonda.config.model.TimeStamped;
import com.example.toonda.rest.comment.dto.CommentRequestDto;
import com.example.toonda.rest.diary.entity.Diary;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="diary_id", nullable = false)
    private Diary diary;

    @Column(nullable = false)
    private String comment;

    @Column
    private boolean recommented = false;

    @Column
    private boolean deleted = false;

    public Comment(User user, Diary diary, CommentRequestDto requestDto) {
        this.user = user;
        this.diary = diary;
        this.comment = requestDto.getComment();
    }

    public void updateComment() {
        this.recommented = true;
    }

    public void deleteComment() {
        this.deleted = true;
    }

}
