package com.wolf.workflow.board.entity;

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

