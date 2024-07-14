package com.wolf.workflow.column.controller;


import com.wolf.workflow.column.dto.request.ColumnMoveRequestDto;
import com.wolf.workflow.column.dto.request.ColumnRequestDto;
import com.wolf.workflow.column.dto.response.ColumnResponseDto;
import com.wolf.workflow.column.service.ColumnService;
import jakarta.validation.Valid;
import java.util.List;
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
  public ResponseEntity<Void> createColumn(@Valid @RequestBody ColumnRequestDto requestDto) {
    columnService.createColumn(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<List<ColumnResponseDto>> getAllColumns() {
    List<ColumnResponseDto> columnResponseDtoList = columnService.getAllColumns();
    return ResponseEntity.ok(columnResponseDtoList);
  }

  @PutMapping("/{columnId}")
  public ResponseEntity<Void> updateColumn(@PathVariable Long columnId, @Valid @RequestBody ColumnRequestDto requestDto) {
    columnService.updateColumn(columnId, requestDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{columnId}")
  public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId, @RequestParam Long userId) {
    columnService.deleteColumn(columnId, userId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/move")
  public ResponseEntity<Void> moveColumn(@Valid @RequestBody ColumnMoveRequestDto columnMoveRequestDto) {
    columnService.moveColumn(columnMoveRequestDto);
    return ResponseEntity.ok().build();
  }

}
