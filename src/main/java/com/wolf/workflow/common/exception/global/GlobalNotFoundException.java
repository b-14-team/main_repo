package com.wolf.workflow.common.exception.global;

import lombok.Getter;

public class GlobalNotFoundException extends RuntimeException {

    public GlobalNotFoundException(String message) {
        super(message);
    }
}
