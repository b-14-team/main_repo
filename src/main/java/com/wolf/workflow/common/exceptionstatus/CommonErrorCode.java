package com.wolf.workflow.common.exceptionstatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()),
    ;

    private final int statusCode;
    private final String reasonPhrase;
}
