package com.wolf.workflow.common.exception.global;

public abstract class GlobalNotFoundException extends RuntimeException {

    public GlobalNotFoundException(String message) {
        super(message);
    }
}
