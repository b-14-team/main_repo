package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalMismatchException;

public class PasswordMismatchException extends GlobalMismatchException {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
