package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalDuplicatedException;

public class DuplicatedEmailException extends GlobalDuplicatedException {

    public DuplicatedEmailException(String message) {
        super(message);
    }

}
