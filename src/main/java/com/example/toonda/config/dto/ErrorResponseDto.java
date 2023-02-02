package com.example.toonda.config.dto;

import com.example.toonda.config.exception.errorcode.Code;
import lombok.Getter;

@Getter
public class ErrorResponseDto extends ResponseDto{

    private ErrorResponseDto(Code errorCode) {
        super(false, errorCode.getStatusCode().value(), errorCode.getStatusMsg());
    }

    private ErrorResponseDto(Code errorCode, Exception e) {
        super(false, errorCode.getStatusCode().value(), errorCode.getStatusMsg(e));
    }

    private ErrorResponseDto(Code errorCode, String message) {
        super(false, errorCode.getStatusCode().value(), errorCode.getStatusMsg(message));
    }


    public static ErrorResponseDto of(Code errorCode) {
        return new ErrorResponseDto(errorCode);
    }

    public static ErrorResponseDto of(Code errorCode, Exception e) {
        return new ErrorResponseDto(errorCode, e);
    }

    public static ErrorResponseDto of(Code errorCode, String message) {
        return new ErrorResponseDto(errorCode, message);
    }

}
