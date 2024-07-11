package com.wolf.workflow.common.exception.global;

import com.wolf.workflow.common.exceptionstatus.ErrorCode;
import lombok.Getter;

public class GlobalMismatchException extends RuntimeException {

    public GlobalMismatchException(String message) {
        super(message);
    }
}
