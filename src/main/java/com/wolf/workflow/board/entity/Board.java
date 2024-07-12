package com.wolf.workflow.board.entity;

import com.wolf.workflow.board.dto.request.BoardRequestDto;
import com.wolf.workflow.common.Timestamped;
import com.wolf.workflow.user.entity.User;
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
    private Board(String board_name, String content) {
        this.board_name = board_name;
        this.content =content;
    }
    public static Board createBoard(BoardRequestDto requestDto, User user) {
        return Board.builder()
            .board_name(requestDto.getBoard_name())
            .content(requestDto.getContent())
            .build();
    }
    public void updateBoard(String board_name,String content, Long userId) {
        this.board_name = board_name;
        this.content = content;
    }

}
