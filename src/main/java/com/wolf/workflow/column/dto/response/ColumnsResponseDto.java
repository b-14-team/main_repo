package com.wolf.workflow.column.dto.response;

import com.wolf.workflow.card.dto.response.CardGetAllResponseDto;
import com.wolf.workflow.card.dto.response.CardsGetByColumnId;
import com.wolf.workflow.column.entity.Columns;
import lombok.Getter;

import java.util.List;

@Getter
public class ColumnsResponseDto {
    private final Long id;
    private final String columnsStatus;
    private final Long numberOfCard;
    private final Long maxCards;
    private final String color;
    private final List<CardsGetByColumnId> cardList; // 카드 목록 추가

    public ColumnsResponseDto(Columns columns, List<CardsGetByColumnId> cardList) {
        this.id = columns.getId();
        this.columnsStatus = columns.getColumnsStatus();
        this.numberOfCard = columns.getNumberOfCard();
        this.maxCards = columns.getMaxCards();
        this.color = columns.getColor();
        this.cardList = cardList; // 카드 목록 초기화
    }
}

