package com.wolf.workflow.common.exceptionstatus;

import org.springframework.context.MessageSource;

public interface ErrorCode {

    int getStatusCode();

    String getReasonPhrase();
}
