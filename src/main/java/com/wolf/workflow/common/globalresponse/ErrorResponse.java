package com.wolf.workflow.common.globalresponse;

import com.wolf.workflow.common.exceptionstatus.ErrorCode;

public class ErrorResponse<T> extends ApiResponse {

    protected ErrorResponse(int httpCode, String httpCodeName, String message) {
        super(httpCode, httpCodeName, message, null);
    }

    protected ErrorResponse(int httpCode, String httpCodeName, String message, T data) {
        super(httpCode, httpCodeName, message, data);
    }

    public static <T> ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse<>(
                errorCode.getStatusCode(),
                errorCode.getReasonPhrase(),
                message);
    }

    public static <T> ErrorResponse of(ErrorCode errorCode, String message, T data) {
        return new ErrorResponse<>(
                errorCode.getStatusCode(),
                errorCode.getReasonPhrase(),
                message,
                data);
    }
}
