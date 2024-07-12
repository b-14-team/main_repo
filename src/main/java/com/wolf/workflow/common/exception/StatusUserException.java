package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalStatusException;

public class StatusUserException extends GlobalStatusException {

    public StatusUserException(String message) {
        super(message);
    }
}
