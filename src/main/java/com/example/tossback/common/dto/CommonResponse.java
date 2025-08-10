package com.example.tossback.common.dto;

import com.example.tossback.common.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class CommonResponse {
    private String code = ErrorCode.NO_ERROR.getCode();
    private String message = "요청 성공";
    private Object result;

    public CommonResponse(Object result) {
        this.result = result;
    }

    public CommonResponse(String message, Object result) {
        this.message = message;
        this.result = result;
    }

    public CommonResponse(String code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
