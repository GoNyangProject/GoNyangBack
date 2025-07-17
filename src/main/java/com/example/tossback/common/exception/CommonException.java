package com.example.tossback.common.exception;

import com.example.tossback.common.enums.ErrorCode;
import lombok.Getter;


@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommonException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}