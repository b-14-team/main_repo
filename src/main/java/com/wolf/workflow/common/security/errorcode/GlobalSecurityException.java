package com.wolf.workflow.common.security.errorcode;

import com.wolf.workflow.common.exceptionstatus.ErrorCode;

public interface GlobalSecurityException {
    ErrorCode getErrorCode();

    String getErrorDescription();
}
