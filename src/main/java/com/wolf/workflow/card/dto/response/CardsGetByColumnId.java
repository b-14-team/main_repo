package com.wolf.workflow.card.dto.response;

import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.column.entity.Columns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CardsGetByColumnId {
    private Long id;
    private String title;
    private String content;
    private LocalDate deadDate;
    private Long assigneeId;
    private String assignee;
    private String cardStatus;
    private LocalDateTime createdAt;


    public static CardsGetByColumnId of(Card card, Columns columns, String assignee) {
        return CardsGetByColumnId.builder()
                .id(card.getId())
                .title(card.getTitle())
                .content(card.getContent())
                .deadDate(card.getDeadDate())
                .assigneeId(card.getAssigneeId())
                .assignee(assignee)
                .cardStatus(columns.getColumnsStatus())
                .createdAt(card.getCreatedAt())
                .build();
    }
}
