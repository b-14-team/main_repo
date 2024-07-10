package com.wolf.workflow.board.entity;

import com.wolf.workflow.common.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name", nullable = false)
    private String board_name;

    @Column(name = "content")
    private String content;

    @Builder
    public Board(String board_name, String content) {
        this.board_name = board_name;
        this.content =content;
    }

}