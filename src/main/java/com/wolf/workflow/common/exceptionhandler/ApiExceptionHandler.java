package com.wolf.workflow.common.exceptionhandler;

import com.wolf.workflow.common.exception.global.GlobalDuplicatedException;
import com.wolf.workflow.common.exceptionstatus.CommonErrorCode;
import com.wolf.workflow.common.globalresponse.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "ApiException")
@Order(1)
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GlobalDuplicatedException.class)
    public ResponseEntity<ErrorResponse> globalDuplicatedException(GlobalDuplicatedException e) {
        log.error("GlobalDuplicatedException 발생");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ErrorResponse.of(CommonErrorCode.BAD_REQUEST, e.getErrorMessage()));
    }


}
