package com.wolf.workflow.common.exception.global;

public abstract class GlobalDuplicatedException extends RuntimeException {

    public GlobalDuplicatedException(String message) {
        super(message);
    }
}
