package com.wolf.workflow.card.entity;

import com.wolf.workflow.card.dto.request.CardCreateRequestDto;
import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.common.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private Long assigneeId;

    private LocalDateTime deadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columns_id",nullable = false)
    private Columns columns;

    @Builder
    private Card(String title, String content, Long assigneeId, LocalDateTime deadDate,Columns columns) {
        this.title = title;
        this.content = content;
        this.assigneeId = assigneeId;
        this.deadDate = deadDate;
        this.columns = columns;
    }

    public void updateCard(String title, String content, Long assigneeId, LocalDateTime deadDate) {
        this.title = title;
        this.content = content;
        this.assigneeId = assigneeId;
        this.deadDate = deadDate;
    }

    public static Card createCard(CardCreateRequestDto requestDto, Columns columns) {
        return Card.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .assigneeId(requestDto.getAssigneeId())
                .deadDate(requestDto.getDeadDate())
                .columns(columns)
                .build();
    }
}
