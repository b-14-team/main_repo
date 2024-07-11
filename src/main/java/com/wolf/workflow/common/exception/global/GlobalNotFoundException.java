package com.wolf.workflow.common.exception.global;

import lombok.Getter;

@Getter
public class GlobalNotFoundException extends RuntimeException {

    private final String errorMessage;

    public GlobalNotFoundException(String message) {
        super(message);
        this.errorMessage = message;
    }
}
