package com.example.tossback.common.exception;

import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> baseCustomException(RuntimeException exception) {
        String errorMessage = exception.getMessage();
        String errorCode = ErrorCode.UNKNOWN.getCode();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (exception instanceof CommonException commonException) {
            errorMessage = commonException.getErrorCode().getMessage();
            status = commonException.getErrorCode().getHttpStatus();
        }
        CommonResponse body = new CommonResponse(
                errorCode,
                errorMessage,
                new ArrayList<>()
        );
        log.error("errorCode = {}, httpStatus = {}, errorMessage = {}", errorCode, status, errorMessage);

        return new ResponseEntity<>(body, status);
    }
}
