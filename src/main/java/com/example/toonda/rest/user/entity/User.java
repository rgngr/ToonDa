package com.example.toonda.rest.user.entity;

import com.example.toonda.rest.user.dto.SignupRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity(name = "Users")
@Getter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String img;

    @Column
    private String introduction;

    @Column
    private boolean deleted = false;

    @Column(unique = true)
    private Long kakaoId;

    public User (SignupRequestDto requestDto, String password) {
        this.email = requestDto.getEmail();
        this.username = requestDto.getUsername();
        this.password = password;
    }

}
