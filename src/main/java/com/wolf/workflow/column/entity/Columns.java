package com.wolf.workflow.column.entity;

import com.wolf.workflow.board.entity.Board;
import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.common.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "columns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Columns extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "columns_id")
    private Long id;

    @Column(name = "column_status", nullable = false)
    private String columnsStatus;

    @Column(name = "move", nullable = false)
    private int move;

    @Column(name = "number_of_card", nullable = false)
    private Long numberOfCard = 0L;

    @Column(name = "max_card", nullable = false)
    private Long maxCards = -1L;

    @Column(nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @OneToMany(mappedBy = "columns")
    private List<Card> cardList = new ArrayList<>();

    @Builder
    private Columns(String columnsStatus, String color, int move) {
        this.columnsStatus = columnsStatus;
        this.color = color;
        this.move = move;
    }

    public static Columns createColumn(String columnsStatus, String color, int move) {
        return Columns.builder()
            .columnsStatus(columnsStatus)
            .color(color)
            .move(move)
            .build();
    }

    public void updateColumn(String columnsStatus, String color) {
        this.columnsStatus = columnsStatus;
        this.color = color;
    }

    public void ColumnMove(int move) {
        this.move = move;
    }
}
