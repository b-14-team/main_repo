package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundColumnException extends GlobalNotFoundException {
    public NotFoundColumnException(String message) {
        super(message);
    }
}
