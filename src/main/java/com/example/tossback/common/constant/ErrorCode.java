package com.example.tossback.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NO_ERROR("0000", "요청 성공", HttpStatus.OK);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    private ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
