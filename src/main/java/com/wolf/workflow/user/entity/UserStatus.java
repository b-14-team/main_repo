package com.wolf.workflow.user.entity;

import com.wolf.workflow.common.exception.StatusUserException;
import com.wolf.workflow.common.util.MessageUtil;
import java.util.Locale;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String userStatusName;
    public static void checkUserStatus(UserStatus userRole) {
        if (Objects.equals(userRole, DISABLE)) {
            throw new StatusUserException(MessageUtil.getMessage("disable.status.user"));
        }
    }
}
