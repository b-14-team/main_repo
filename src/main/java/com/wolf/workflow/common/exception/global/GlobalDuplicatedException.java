package com.wolf.workflow.common.exception.global;

import lombok.Getter;

public class GlobalDuplicatedException extends RuntimeException {

    public GlobalDuplicatedException(String message) {
        super(message);
    }
}
