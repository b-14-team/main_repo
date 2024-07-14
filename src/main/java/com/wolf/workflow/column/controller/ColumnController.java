package com.wolf.workflow.column.controller;


import com.wolf.workflow.column.dto.request.ColumnMoveRequestDto;
import com.wolf.workflow.column.dto.request.ColumnRequestDto;
import com.wolf.workflow.column.dto.response.ColumnResponseDto;
import com.wolf.workflow.column.service.ColumnService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/columns")
@RequiredArgsConstructor
public class ColumnController {

  private final ColumnService columnService;

  @PostMapping
  public ResponseEntity<ApiResponse<ColumnResponseDto>> createColumn(@RequestParam Long boardId,
      @RequestParam Long userId,
      @Valid @RequestBody ColumnRequestDto requestDto) {
    ColumnResponseDto responseDto = columnService.createColumn(boardId, userId, requestDto);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.of(responseDto));
  }

  @PutMapping("/{columnId}")
  public ResponseEntity<ApiResponse<ColumnResponseDto>> updateColumn(@PathVariable Long columnId,
      @RequestParam Long boardId,
      @RequestParam Long userId,
      @Valid @RequestBody ColumnRequestDto requestDto) {
    ColumnResponseDto responseDto = columnService.updateColumn(boardId, userId, columnId,
        requestDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of(responseDto));
  }

  @DeleteMapping("/{columnId}")
  public ResponseEntity<ApiResponse<String>> deleteColumn(@PathVariable Long columnId,
      @RequestParam Long boardId,
      @RequestParam Long userId) {
    columnService.deleteColumn(boardId, columnId, userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of("칼럼이 삭제되었습니다."));
  }

  @PutMapping("/move")
  public ResponseEntity<ApiResponse<String>> moveColumn(
      @Valid @RequestBody ColumnMoveRequestDto columnMoveRequestDto,
      @RequestParam Long userId,
      @RequestParam Long boardId) {
    columnService.moveColumn(boardId, userId, columnMoveRequestDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of("칼럼이 이동되었습니다."));
  }
}


