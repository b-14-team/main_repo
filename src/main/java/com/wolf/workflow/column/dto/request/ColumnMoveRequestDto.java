package com.wolf.workflow.column.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnMoveRequestDto {
  private List<Long> columnIds;
  private Long userId;
}