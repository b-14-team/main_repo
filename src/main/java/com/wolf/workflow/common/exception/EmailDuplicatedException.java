package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalDuplicatedException;

public class EmailDuplicatedException extends GlobalDuplicatedException {

    public EmailDuplicatedException(String message) {
        super(message);
    }

}
