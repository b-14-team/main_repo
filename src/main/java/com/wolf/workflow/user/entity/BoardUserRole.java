package com.wolf.workflow.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardUserRole {
    CONSUMER("USER"),
    MANAGER("MANAGER"),
    ;

    private final String userRoleName;
}

