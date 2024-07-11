package com.wolf.workflow.common.exception.global;

import lombok.Getter;

@Getter
public class GlobalDuplicatedException extends RuntimeException {

    private final String errorMessage;

    public GlobalDuplicatedException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
