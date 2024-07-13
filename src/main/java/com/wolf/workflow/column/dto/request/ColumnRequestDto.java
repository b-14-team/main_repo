package com.wolf.workflow.column.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ColumnRequestDto {
  @NotBlank(message = "공백 없이 입력해야합니다.")
  private String columnsStatus;
  private String color;
  private Long userId;
}