package com.example.toonda.rest.comment.entity;

import com.example.toonda.config.model.TimeStamped;
import com.example.toonda.rest.comment.dto.RecommentRequestDto;
import com.example.toonda.rest.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Recomment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private String recomment;

    @Column
    private boolean rrecommented = false;

    @Column
    private boolean deleted = false;

    public Recomment(User user, Comment comment, RecommentRequestDto requestDto) {
        this.user = user;
        this.comment = comment;
        this.recomment = requestDto.getRecomment();
    }

    public void updateRecomment() {
        this.rrecommented = true;
    }

    public void deleteRecomment() {
        this.deleted = true;
    }

}
