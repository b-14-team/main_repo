package com.wolf.workflow.column.controller;


import com.wolf.workflow.column.dto.request.ColumnRequestDto;
import com.wolf.workflow.column.service.ColumnService;
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

  @Valid
  @PostMapping
  public ResponseEntity<Void> createColumn(@RequestBody ColumnRequestDto requestDto) {
    columnService.createColumn(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Valid
  @PutMapping("/{columnId}")
  public ResponseEntity<Void> updateColumn(@PathVariable Long columnId, @RequestBody ColumnRequestDto requestDto) {
    columnService.updateColumn(columnId, requestDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{columnId}")
  public ResponseEntity<Void> deleteColumn(@PathVariable Long columnId) {
    columnService.deleteColumn(columnId);
    return ResponseEntity.noContent().build();
  }
}
