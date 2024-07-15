package com.wolf.workflow.common.exception.global;

public abstract class GlobalInvalidException extends RuntimeException {

    public GlobalInvalidException(String message) {
        super(message);
    }
}
