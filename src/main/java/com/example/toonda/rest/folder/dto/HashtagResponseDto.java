package com.example.toonda.rest.folder.dto;

import lombok.Getter;

@Getter
public class HashtagResponseDto {

    private String hashtag;

    public HashtagResponseDto(String hashtag) {
        this.hashtag = hashtag;
    }

}
