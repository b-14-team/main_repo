package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundBoardUserException extends GlobalNotFoundException {

    public NotFoundBoardUserException(String message) {
        super(message);
    }
}
