package com.wolf.workflow.column.service;

import com.wolf.workflow.column.adapter.ColumnAdapter;
import com.wolf.workflow.column.dto.request.ColumnRequestDto;
import com.wolf.workflow.column.dto.response.ColumnResponseDto;
import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.common.exception.DuplicatedColumnException;
import com.wolf.workflow.common.exception.NotFoundColumnException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ColumnService {

  private final ColumnAdapter columnAdapter;

  /**
   * 컬럼 생성하기
   * @param requestDto 컬럼 요청 DTO
   * @return 생성된 컬럼 응답 DTO
   * @throws DuplicatedColumnException 이미 동일한 상태의 컬럼이 존재할 때
   */

  public ColumnResponseDto createColumn(ColumnRequestDto requestDto) {

    Columns columns = Columns.createColumn(requestDto.getColumnsStatus(), requestDto.getColor());
    columnAdapter.createColumn(columns);
    return ColumnResponseDto.of(columns);
  }


  /**
   * 컬럼 업데이트
   * @param columnId 업데이트할 컬럼 ID
   * @param requestDto 업데이트할 데이터가 담긴 컬럼 요청 DTO
   * @return 업데이트된 컬럼 응답 DTO
   * @throws NotFoundColumnException 주어진 ID로 컬럼을 찾을 수 없을 때
   */
  @Transactional
  public ColumnResponseDto updateColumn(Long columnId, ColumnRequestDto requestDto) {
    Columns columns = columnAdapter.findColumnsById(columnId);
    columns.updateColumn(requestDto.getColumnsStatus(), requestDto.getColor());
    return ColumnResponseDto.of(columns);
  }

  /**
   * 컬럼 삭제
   * @param columnId 삭제할 컬럼 ID
   * @throws NotFoundColumnException 주어진 ID로 컬럼을 찾을 수 없을 때
   */
  @Transactional
  public void deleteColumn(Long columnId) {
    Columns columns = columnAdapter.findColumnsById(columnId);
    columnAdapter.deleteColumn(columns);
  }
}
