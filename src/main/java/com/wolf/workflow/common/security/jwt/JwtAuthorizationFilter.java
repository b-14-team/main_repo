package com.wolf.workflow.common.security.jwt;

import com.wolf.workflow.common.exceptionstatus.CommonErrorCode;
import com.wolf.workflow.common.security.AuthenticationUserService;
import com.wolf.workflow.common.security.exception.CustomSecurityException;
import com.wolf.workflow.common.util.MessageUtil;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationUserService authenticationUserService;
    private final UserAdapter userAdapter;

    public JwtAuthorizationFilter(JwtUtil jwtUtil,
            AuthenticationUserService authenticationUserService, UserAdapter userAdapter) {
        this.jwtUtil = jwtUtil;
        this.authenticationUserService = authenticationUserService;
        this.userAdapter = userAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = jwtUtil.getAccessTokenFromHeader(req);
        log.info("현재주소 : "+req.getRequestURL().toString());
                // 인가 요청시 확인 없이 넘어가야하는 주소들 추가

            log.info("access token 검증");
            if (StringUtils.hasText(accessTokenValue)
                    && jwtUtil.validateToken(req, accessTokenValue)
                    && !req.getRequestURL().toString().equals("http://localhost:8080/")
                    && !req.getRequestURL().toString().contains("/css/")
                    && !req.getRequestURL().toString().contains("/js/")
                    && !req.getRequestURL().toString().contains("/favicon.ico")
//                && !req.getRequestURL().toString().contains("/api/users/login")
            ) {
                log.info("refresh token 검증");

                String email = jwtUtil.getUserInfoFromToken(accessTokenValue).getSubject();
                User findUser = userAdapter.getUserByEmail(email);

                if (findUser.getRefreshToken() != null) {
                    if (isValidateUserEmail(email, findUser)) {

                        log.info("Token 인증 완료");
                        Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                        setAuthentication(info.getSubject());
                    }
                } else {
                    log.error("유효하지 않는 Refersh Token");
                    req.setAttribute("exception",
                            new CustomSecurityException(
                                    CommonErrorCode.BAD_REQUEST,
                                    MessageUtil.getMessage("invalid.jwt.signature")));
                }
            }
        filterChain.doFilter(req, res);
    }

    private boolean isValidateUserEmail(String email, User findUser) {
        return email.equals(findUser.getEmail());
    }


    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = authenticationUserService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
    }
}