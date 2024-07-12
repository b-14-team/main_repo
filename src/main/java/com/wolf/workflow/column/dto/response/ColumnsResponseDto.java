package com.wolf.workflow.column.dto.response;

import com.wolf.workflow.board.dto.response.BoardResponseDto;
import com.wolf.workflow.board.entity.Board;
import com.wolf.workflow.card.dto.response.CardGetResponseDto;
import com.wolf.workflow.column.entity.Columns;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ColumnsResponseDto {
    private final Long id;
    private final String columnsStatus;
    private final Long numberOfCard;
    private final Long maxCards;
    private final String color;


    public ColumnsResponseDto(Columns columns) {
        this.id = columns.getId();
        this.columnsStatus = columns.getColumnsStatus();
        this.numberOfCard = columns.getNumberOfCard();
        this.maxCards = columns.getMaxCards();
        this.color = columns.getColor();
    }
    /*
    private Long id;
    private String columnsStatus;
    private Long numberOfCard = 0L;
    private Long maxCards = -1L;
    private String color;
    private Board board;
    private List<Card> cardList = new ArrayList<>();
     */
}
