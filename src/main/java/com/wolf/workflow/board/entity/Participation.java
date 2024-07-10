package com.wolf.workflow.board.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Participation {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;

    private final String participationName;
}
