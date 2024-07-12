package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalInvalidException;

public class InvalidDeadDateException extends GlobalInvalidException {
    public InvalidDeadDateException(String message) {
        super(message);
    }
}
