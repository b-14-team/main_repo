package com.wolf.workflow.common.exception;

import com.wolf.workflow.common.exception.global.GlobalDuplicatedException;

public class DuplicatedColumnException extends GlobalDuplicatedException  {
  public DuplicatedColumnException(String message) {
    super(message);
  }

}
