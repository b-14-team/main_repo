package com.wolf.workflow.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
  @NotBlank(message = "보드 이름은 필수입니다")
  private String board_name;
  @NotBlank(message = "한 줄 설명은 필수입니다")
  private String content;
  private Long userId;
}
