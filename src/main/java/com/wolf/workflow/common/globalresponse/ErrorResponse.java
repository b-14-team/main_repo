package com.wolf.workflow.common.globalresponse;

import com.wolf.workflow.common.exceptionstatus.ErrorCode;

public class ErrorResponse<T> extends ApiResponse {

    protected ErrorResponse(int httpCode, String httpCodeName, String message) {
        super(httpCode, httpCodeName, message, null);
    }

    public static <T> ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse<>(
                errorCode.getStatusCode(),
                errorCode.getReasonPhrase(),
                message);
    }
}
