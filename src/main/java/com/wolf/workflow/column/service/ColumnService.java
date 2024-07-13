package com.wolf.workflow.column.service;

import com.wolf.workflow.column.adapter.ColumnAdapter;
import com.wolf.workflow.column.dto.request.ColumnMoveRequestDto;
import com.wolf.workflow.column.dto.request.ColumnRequestDto;
import com.wolf.workflow.column.dto.response.ColumnResponseDto;
import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.common.exception.DuplicatedColumnException;
import com.wolf.workflow.common.exception.NotFoundColumnException;
import com.wolf.workflow.common.exception.NotFoundUserException;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnService {

  private final ColumnAdapter columnAdapter;
  private final UserAdapter userAdapter;

  /**
   * 컬럼 생성하기
   *
   * @param requestDto 컬럼 요청 DTO
   * @return 생성된 컬럼 응답 DTO
   * @throws DuplicatedColumnException 이미 동일한 상태의 컬럼이 존재할 때
   * @throws NotFoundUserException  사용자를 찾을 수 없음
   */
  @Transactional
  public ColumnResponseDto createColumn(ColumnRequestDto requestDto) {
    User user = getUserFromRequest(requestDto);

    Columns columns = Columns.createColumn(requestDto.getColumnsStatus(), requestDto.getColor());
    columnAdapter.createColumn(columns);

    return ColumnResponseDto.of(columns);
  }

  /**
   * 모든 칼럼 조회
   *
   * @return 전체 칼럼 리스트
   */
  @Transactional(readOnly = true)
  public List<ColumnResponseDto> getAllColumns() {
    List<Columns> columnsList = columnAdapter.getAllColumns();
    return columnsList.stream()
        .map(ColumnResponseDto::of)
        .collect(Collectors.toList());
  }

  /**
   * 컬럼 업데이트
   *
   * @param columnId   업데이트할 컬럼 ID
   * @param requestDto 업데이트할 데이터가 담긴 컬럼 요청 DTO
   * @return 업데이트된 컬럼 응답 DTO
   * @throws NotFoundColumnException 주어진 ID로 컬럼을 찾을 수 없을 때
   * @throws NotFoundUserException  사용자를 찾을 수 없음
   */
  @Transactional
  public ColumnResponseDto updateColumn(Long columnId, ColumnRequestDto requestDto) {
    User user = getUserFromRequest(requestDto);

    Columns columns = columnAdapter.findColumnsById(columnId);
    columns.updateColumn(requestDto.getColumnsStatus(), requestDto.getColor());
    columnAdapter.updateColumn(columns);

    return ColumnResponseDto.of(columns);
  }

  /**
   * 컬럼 삭제
   *
   * @param columnId 삭제할 컬럼 ID
   * @param userId   사용자 ID
   * @throws NotFoundColumnException 주어진 ID로 컬럼을 찾을 수 없을 때
   * @throws NotFoundUserException  사용자를 찾을 수 없음
   */
  @Transactional
  public void deleteColumn(Long columnId, Long userId) {
    User user = userAdapter.getUserById(userId);

    Columns columns = columnAdapter.findColumnsById(columnId);
    columnAdapter.deleteColumn(columns);
  }

  /**
   * 컬럼 순서 이동
   *
   * @param columnOrderRequestDto 컬럼 순서 요청 DTO
   * @throws NotFoundColumnException 주어진 ID로 컬럼을 찾을 수 없을 때
   * @throws NotFoundUserException  사용자를 찾을 수 없음
   */
  @Transactional
  public void moveColumn(ColumnMoveRequestDto columnOrderRequestDto) {
    User user = userAdapter.getUserById(columnOrderRequestDto.getUserId());

    List<Long> columnIds = columnOrderRequestDto.getColumnIds();
    for (int i = 0; i < columnIds.size(); i++) {
      Columns column = columnAdapter.findColumnsById(columnIds.get(i));
      column.ColumnMove(i);
      columnAdapter.updateColumn(column);
    }
  }

  /**
   * 요청 DTO에서 사용자 ID를 통해 사용자를 조회합니다.
   *
   * @param requestDto 요청 DTO
   * @return 조회된 사용자 객체
   * @throws NotFoundUserException 사용자를 찾을 수 없음
   */
  private User getUserFromRequest(ColumnRequestDto requestDto) {
    return userAdapter.getUserById(requestDto.getUserId());
  }
}
