package com.example.toonda.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity(name = "users")
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

    private String img;

    private String introduction;

    private boolean deleted = false;

    @Column(unique = true)
    private Long kakaoId;

    public User (String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
