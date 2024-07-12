package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalNotFoundException;

public class NotFoundBoardException extends GlobalNotFoundException {
  public NotFoundBoardException(String message) {
    super(message);
  }
}
