package com.wolf.workflow.common.exception.global;

public abstract class GlobalStatusException extends RuntimeException {

    public GlobalStatusException(String message) {
        super(message);
    }
}
