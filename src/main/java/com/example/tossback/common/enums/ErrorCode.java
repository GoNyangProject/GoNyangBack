package com.example.tossback.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNAUTHORIZED_ACCESS("0403", "접근 및 조작 권한이 없습니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("0500", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_ERROR("0000", "요청 성공", HttpStatus.OK),
    UNKNOWN("9999", "알 수 없는 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    Unauthorized("0401", "ID 혹은 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    FAIL_JOIN("0001", "회원 가입 실패", HttpStatus.BAD_REQUEST),
    FAIL_BOOK("0409", "예약 실패", HttpStatus.CONFLICT),
    INVALID_PARAMETER("0400", "잘못된 요청 파라미터입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_DATA("0404", "해당 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_BLOCKED("0408", "이미 차단된 날짜입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    private ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
