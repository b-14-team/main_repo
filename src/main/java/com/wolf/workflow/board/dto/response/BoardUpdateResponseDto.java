package com.wolf.workflow.board.dto.response;

import com.wolf.workflow.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardUpdateResponseDto {
  private  Long boardId;
  private  String board_name;
  private  String content;


  public static BoardResponseDto of(Board board) {
    return BoardResponseDto.builder()
        .boardId(board.getId())
        .board_name(board.getBoard_name())
        .content(board.getContent())
        .build();


  }
}
