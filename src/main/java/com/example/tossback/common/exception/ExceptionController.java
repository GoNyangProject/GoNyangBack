package com.example.tossback.common.exception;

import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> handleRuntimeException(RuntimeException exception) {
        String errorMessage = exception.getMessage();
        String errorCode = ErrorCode.UNKNOWN.getCode();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (exception instanceof CommonException commonException) {
            errorMessage = commonException.getMessage();
            status = commonException.getErrorCode().getHttpStatus();
        }

        CommonResponse commonResponse = new CommonResponse(
                errorCode,
                errorMessage,
                new ArrayList<>()
        );
        log.error("errorCode = {}, errorMessage = {}, httpStatus = {}", errorCode, errorMessage, status);
        return new ResponseEntity<>(commonResponse, status);
    }

}
