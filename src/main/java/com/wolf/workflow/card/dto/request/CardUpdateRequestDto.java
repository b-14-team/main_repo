package com.wolf.workflow.card.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardUpdateRequestDto {
    private String title;
    private String content;
    private LocalDateTime deadDate;
    private Long assigneeId;
}
