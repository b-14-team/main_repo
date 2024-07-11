package com.wolf.workflow.comment.controller;

import com.wolf.workflow.comment.dto.request.CommentRequestDto;
import com.wolf.workflow.comment.dto.response.CommentResponseDto;
import com.wolf.workflow.comment.service.CommentService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/comments/{cardId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(responseDto));

    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getComments() {
        List<CommentResponseDto> responseDtoList = commentService.getAllComment();
        return ResponseEntity.ok()
                .body(ApiResponse.of(responseDtoList));
    }
}
