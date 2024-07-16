package com.wolf.workflow.common.security.jwt;

import static com.wolf.workflow.common.exceptionstatus.CommonErrorCode.BAD_REQUEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolf.workflow.common.globalresponse.ErrorResponse;
import com.wolf.workflow.common.security.exception.CustomSecurityException;
import com.wolf.workflow.common.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j(topic = "인증 예외 필터")
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        log.error("인증이 필요합니다.");
        response.sendRedirect("/login");
//        Exception exception = (Exception) request.getAttribute("exception");
//
//        if (exception instanceof CustomSecurityException e) {
//            sendErrorResponse(response, e.getErrorDescription());
//            return;
//        }
//
//        sendErrorResponse(response, MessageUtil.getMessage("invalid.jwt.signature"));
//    }
//
//    private void sendErrorResponse(HttpServletResponse response, String message)
//            throws IOException {
//        ErrorResponse errorResponse = ErrorResponse.of(BAD_REQUEST, message);
//        String body = objectMapper.writeValueAsString(errorResponse);
//
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(BAD_REQUEST.getStatusCode());
//        response.getWriter().write(body);
    }

}