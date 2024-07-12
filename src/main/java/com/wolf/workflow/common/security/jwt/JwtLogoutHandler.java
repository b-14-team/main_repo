package com.wolf.workflow.common.security.jwt;

import com.wolf.workflow.common.exceptionstatus.CommonErrorCode;
import com.wolf.workflow.common.security.AuthenticationUser;
import com.wolf.workflow.common.security.exception.CustomSecurityException;
import com.wolf.workflow.common.util.MessageUtil;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final UserAdapter userAdapter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        log.info("로그아웃 시도");
        String accessTokenFromHeader = jwtUtil.getAccessTokenFromHeader(request);
        String refreshTokenFromHeader = jwtUtil.getRefreshTokenFromHeader(request);

        if (accessTokenFromHeader == null || refreshTokenFromHeader == null) {
            log.error("찾을 수 없는 토큰");
            CustomSecurityException e = new CustomSecurityException(
                    CommonErrorCode.BAD_REQUEST, MessageUtil.getMessage("not.found.token"));
            request.setAttribute("exception", e.getErrorDescription());
            throw e;
        }

        User user = ((AuthenticationUser) authentication.getPrincipal()).getUser();
        if (!Objects.equals(refreshTokenFromHeader, user.getRefreshToken())) {
            log.error("일치하지 않는 토큰");
            CustomSecurityException e = new CustomSecurityException(
                    CommonErrorCode.BAD_REQUEST,
                    MessageUtil.getMessage("mismatch.token"));
            request.setAttribute("exception", e);
            throw e;
        }
        user.updateRefreshToken(null);
        userAdapter.createUser(user);

        SecurityContextHolder.clearContext();
    }

}