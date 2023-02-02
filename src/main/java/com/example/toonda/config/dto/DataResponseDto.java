package com.example.toonda.config.dto;

import com.example.toonda.config.exception.errorcode.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final T data;

    private DataResponseDto(T data) {
        super(true, Code.OK.getStatusCode().value(), Code.OK.getStatusMsg());
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, Code.OK.getStatusCode().value(), message);
        this.data = data;
    }

    private DataResponseDto(T data, Code code) {
        super(true, code.getStatusCode().value(), code.getStatusMsg());
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto<>(data, message);
    }

    public static <T> DataResponseDto<T> empty() {
        return new DataResponseDto<>(null);
    }

}
