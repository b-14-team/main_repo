package com.wolf.workflow.common.globalresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final int statusCode; // 200
    private final String reasonPhrase;  // "OK"
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    @Builder
    protected ApiResponse(int statusCode, String reasonPhrase, String message, T data) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(200, "OK","성공", data);
    }

    public static <T> ApiResponse<T> of(String message) {
        return new ApiResponse<>(200, "OK",message, null);
    }

    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(200, "OK",message, data);
    }

}
