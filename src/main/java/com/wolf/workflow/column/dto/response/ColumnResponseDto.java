package com.wolf.workflow.column.dto.response;

import com.wolf.workflow.column.entity.Columns;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColumnResponseDto {

  private Long id;
  private String columnsStatus;
  private String color;

  public static ColumnResponseDto of(Columns columns) {
    return ColumnResponseDto.builder()
        .id(columns.getId())
        .columnsStatus(columns.getColumnsStatus())
        .color(columns.getColor())
        .build();
  }
}
