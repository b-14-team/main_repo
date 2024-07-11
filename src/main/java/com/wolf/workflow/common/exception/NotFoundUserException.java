package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundUserException extends GlobalNotFoundException {

    public NotFoundUserException(String message) {
        super(message);
    }
}
