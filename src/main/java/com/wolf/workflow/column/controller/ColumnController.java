package com.wolf.workflow.column.controller;


import com.wolf.workflow.column.dto.request.ColumnRequestDto;
import com.wolf.workflow.column.dto.response.ColumnResponseDto;
import com.wolf.workflow.column.dto.response.ColumnsResponseDto;
import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.column.service.ColumnService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "ColumnController")
@RestController
@RequestMapping("/columns")
@RequiredArgsConstructor
public class ColumnController {

  private final ColumnService columnService;

  @GetMapping
  public List<ColumnsResponseDto> getColumns() {
    log.info("getColumns");
    return columnService.getAllColumns().stream().map(ColumnsResponseDto::new).toList();
  }

  @PostMapping
  public ResponseEntity<Void> createColumn(@Valid @RequestBody ColumnRequestDto requestDto) {
    columnService.createColumn(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{columnId}")
  public ResponseEntity<Void> updateColumn(@PathVariable Long columnId, @Valid @RequestBody ColumnRequestDto requestDto) {
    columnService.updateColumn(columnId, requestDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{columnId}")
  public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId) {
    columnService.deleteColumn(columnId);
    return ResponseEntity.noContent().build();
  }
}
