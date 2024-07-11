package com.wolf.workflow.common.exception.global;

import com.wolf.workflow.common.exceptionstatus.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalMismatchException extends RuntimeException {

    private final String errorMessage;

    public GlobalMismatchException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
