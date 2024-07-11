package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundCommentException extends GlobalNotFoundException {

    public NotFoundCommentException(String message) {
        super(message);
    }
}
