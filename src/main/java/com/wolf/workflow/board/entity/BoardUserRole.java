package com.wolf.workflow.board.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardUserRole {
    ASSIGNEE("ASSIGNEE"),
    GENERAL_MANAGER("GENERAL_MANAGER"),
    ;

    private final String boardUserRoleName;
}

