package com.wolf.workflow.board.dto.response;

import com.wolf.workflow.board.entity.Board;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardGetResponseDto {
    private Long boardId;
    private String board_name;
    private String content;
    private LocalDateTime createAt;


  public static BoardGetResponseDto of(Board board) {
    return BoardGetResponseDto.builder()
        .boardId(board.getId())
        .board_name(board.getBoard_name())
        .content(board.getContent())
        .createAt(board.getCreatedAt())
        .build();



  }
}