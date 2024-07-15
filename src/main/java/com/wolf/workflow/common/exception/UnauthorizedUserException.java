package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalStatusException;

public class UnauthorizedUserException extends GlobalStatusException {
    public UnauthorizedUserException(String message) {
      super(message);
    }
  }


