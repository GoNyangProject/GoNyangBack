package com.example.tossback.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NO_ERROR("0000", "요청 성공", HttpStatus.OK),
    UNKNOWN("9999", "알 수 없는 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    Unauthorized("0401", "ID 혹은 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    FAIL_JOIN("0001", "회원 가입 실패", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    private ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
