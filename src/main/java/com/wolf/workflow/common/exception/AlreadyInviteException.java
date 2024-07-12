package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalMismatchException;

public class AlreadyInviteException extends GlobalMismatchException {

  public AlreadyInviteException(String message) {
    super(message);
  }
}

