package com.example.tossback.common.dto;

import com.example.tossback.common.constant.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {

    String errorCode = ErrorCode.NO_ERROR.getCode();
    String errorMessage = ErrorCode.NO_ERROR.getMessage();
    Object result;

    public CommonResponse(Object result) {
        this.result = result;
    }

    public CommonResponse(String errorCode, String errorMessage, Object result) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.result = result;
    }
}
