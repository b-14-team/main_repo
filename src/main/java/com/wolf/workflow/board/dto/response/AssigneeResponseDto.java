package com.wolf.workflow.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AssigneeResponseDto {
    private Long assigneeId;
    private String nickName;

    public static AssigneeResponseDto of(Long assigneeId, String nickName) {
        return AssigneeResponseDto.builder()
                .assigneeId(assigneeId)
                .nickName(nickName)
                .build();

    }
}
