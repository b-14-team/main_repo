package com.wolf.workflow.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolf.workflow.common.exceptionstatus.CommonErrorCode;
import com.wolf.workflow.common.globalresponse.ErrorResponse;
import com.wolf.workflow.common.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse
                .of(CommonErrorCode.FORBIDDEN,
                        MessageUtil.getMessage("not.found.user"));

        String body = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
