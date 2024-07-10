package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundCardException extends GlobalNotFoundException {

    public NotFoundCardException(String message) {
        super(message);
    }
}
