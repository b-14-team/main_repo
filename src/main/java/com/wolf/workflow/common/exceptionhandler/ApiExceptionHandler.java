package com.wolf.workflow.common.exceptionhandler;

import com.wolf.workflow.common.exception.global.GlobalDuplicatedException;
import com.wolf.workflow.common.exception.global.GlobalMismatchException;
import com.wolf.workflow.common.exception.global.GlobalNotFoundException;
import com.wolf.workflow.common.exception.global.GlobalStatusException;
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
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CommonErrorCode.BAD_REQUEST, "실패", ex));
    }

    @ExceptionHandler(GlobalDuplicatedException.class)
    public ResponseEntity<ErrorResponse> globalDuplicatedException(GlobalDuplicatedException e) {
        log.error("GlobalDuplicatedException 발생");

        return getResponse(e.getMessage());
    }

    @ExceptionHandler(GlobalMismatchException.class)
    public ResponseEntity<ErrorResponse> globalMismatchException(GlobalMismatchException e) {
        log.error("GlobalMismatchException 발생");

        return getResponse(e.getMessage());
    }

    @ExceptionHandler(GlobalNotFoundException.class)
    public ResponseEntity<ErrorResponse> globalNotFoundException(GlobalNotFoundException e) {
        log.error("GlobalNotFoundException 발생");

        return getResponse(e.getMessage());
    }

    @ExceptionHandler(GlobalStatusException.class)
    public ResponseEntity<ErrorResponse> globalStatusException(GlobalStatusException e) {
        log.error("GlobalStatusException 발생");

        return getResponse(e.getMessage());
    }

    private ResponseEntity<ErrorResponse> getResponse(String e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(CommonErrorCode.BAD_REQUEST, e));
    }


}
