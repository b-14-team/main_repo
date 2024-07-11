package com.wolf.workflow.common.exceptionhandler;

import com.wolf.workflow.common.exception.global.GlobalDuplicatedException;
import com.wolf.workflow.common.exceptionstatus.CommonErrorCode;
import com.wolf.workflow.common.globalresponse.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "ApiException")
@Order(1)
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(GlobalDuplicatedException.class)
    public ResponseEntity<ErrorResponse> globalDuplicatedException(GlobalDuplicatedException e) {
        log.error("GlobalDuplicatedException 발생");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorResponse.of(CommonErrorCode.BAD_REQUEST, e.getErrorMessage()));
    }

}
