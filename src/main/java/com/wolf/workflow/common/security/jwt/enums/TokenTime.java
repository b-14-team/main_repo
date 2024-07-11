package com.wolf.workflow.common.security.jwt.enums;

import com.wolf.workflow.common.exception.global.GlobalServerException;
import com.wolf.workflow.common.util.MessageUtil;
import com.wolf.workflow.user.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenTime {
    USER_TOKEN_TIME(Authority.USER, 1000 * 60 * 60 * 2, 1000 * 60 * 60 * 24 * 3), // 2시간, 3일
    ADMIN_TOKEN_TIME(Authority.ADMIN, 1000 * 60 * 60, 1000 * 60 * 60); // 1시간, 1시간

    private final String authority;
    private final long accessTimeInMillis;
    private final long refreshTimeInMillis;

    public static TokenTime checkUserRole(UserRole userRole) {
        for (TokenTime value : TokenTime.values()) {
            if (value.authority.equalsIgnoreCase(userRole.getUserRoleName())) {
                return value;
            }
        }
        throw new GlobalServerException(MessageUtil.getMessage("internal.server.error"));
    }

    public static class Authority {

        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }
}
