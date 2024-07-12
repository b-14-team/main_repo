package com.wolf.workflow.common.security.jwt;

import static com.wolf.workflow.common.security.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.wolf.workflow.common.security.jwt.JwtConstants.REFRESH_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolf.workflow.common.exceptionstatus.CommonErrorCode;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import com.wolf.workflow.common.globalresponse.ErrorResponse;
import com.wolf.workflow.common.security.AuthenticationUser;
import com.wolf.workflow.common.security.exception.CustomSecurityException;
import com.wolf.workflow.common.security.jwt.dto.AuthRequestDto;
import com.wolf.workflow.common.security.jwt.dto.TokenDto;
import com.wolf.workflow.common.util.MessageUtil;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import com.wolf.workflow.user.entity.UserStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final UserAdapter userAdapter;


    public JwtAuthenticationFilter(ObjectMapper objectMapper, JwtUtil jwtUtil,
            UserAdapter userAdapter
    ) {
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.userAdapter = userAdapter;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            AuthRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                    AuthRequestDto.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(), requestDto.getPassword(), null)
            );
        } catch (IOException e) {
            log.error("attemptAuthentication 예외 발생 {} ", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        log.info("로그인 성공 및 JWT 토큰 발행");
        User user = ((AuthenticationUser) authResult.getPrincipal()).getUser();

        if (user.getUserStatus() == UserStatus.DISABLE) {
            request.setAttribute("exception", new CustomSecurityException(
                    CommonErrorCode.BAD_REQUEST,
                    MessageUtil.getMessage("resign.user")));
            throw new CustomSecurityException(
                    CommonErrorCode.BAD_REQUEST,
                    MessageUtil.getMessage("resign.user"));
        }

        TokenDto tokenDto = jwtUtil
                .generateAccessTokenAndRefreshToken(user.getEmail(), user.getUserRole());
        String refreshTokenValue = tokenDto.getRefreshToken().substring(7);

        user.updateRefreshToken(refreshTokenValue);
        userAdapter.createUser(user);

        loginSuccessResponse(response, tokenDto);
    }


    private void loginSuccessResponse(HttpServletResponse response, TokenDto tokenDto)
            throws IOException {
        ApiResponse apiResponse = ApiResponse.of("로그인 성공");
        String body = objectMapper.writeValueAsString(apiResponse);

        response.setStatus(OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader(ACCESS_TOKEN_HEADER, tokenDto.getAccessToken());
        response.addHeader(REFRESH_TOKEN_HEADER, tokenDto.getRefreshToken());
        response.getWriter().write(body);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("unsuccessfulAuthentication | 로그인 실패");
        String body = objectMapper.writeValueAsString(
                ErrorResponse.of(
                        CommonErrorCode.BAD_REQUEST,
                        MessageUtil.getMessage("not.found.user")));

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);
    }
}