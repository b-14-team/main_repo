package com.wolf.workflow.common.security.exception;

import com.wolf.workflow.common.exceptionstatus.ErrorCode;
import com.wolf.workflow.common.security.errorcode.GlobalSecurityException;
import lombok.Getter;

@Getter
public class CustomSecurityException extends RuntimeException implements GlobalSecurityException {

    private final ErrorCode errorCode;
    private final String errorDescription;

    public CustomSecurityException(ErrorCode errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
