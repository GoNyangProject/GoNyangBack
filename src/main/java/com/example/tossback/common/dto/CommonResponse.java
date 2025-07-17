package com.example.tossback.common.dto;

import com.example.tossback.common.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {

    String errorCode = ErrorCode.NO_ERROR.getCode();
    String message = ErrorCode.NO_ERROR.getMessage();
    Object result;

    public CommonResponse(Object result) {
        this.result = result;
    }
}
