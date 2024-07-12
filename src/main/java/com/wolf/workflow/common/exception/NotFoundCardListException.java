package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundCardListException extends GlobalNotFoundException {

    public NotFoundCardListException(String message) {
        super(message);
    }
}
