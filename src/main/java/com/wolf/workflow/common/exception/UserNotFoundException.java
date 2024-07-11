package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class UserNotFoundException extends GlobalNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
