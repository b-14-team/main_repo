package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalMismatchException;

public class MismatchPasswordException extends GlobalMismatchException {

    public MismatchPasswordException(String message) {
        super(message);
    }
}
