package com.wolf.workflow.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    CONSUMER("USER"),
    MANAGER("MANAGER"),
    ;

    private final String userRoleName;
}
