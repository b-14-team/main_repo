package com.wolf.workflow.comment.controller;

import com.wolf.workflow.comment.dto.request.CommentCreateRequestDto;
import com.wolf.workflow.comment.dto.response.CommentCreateResponseDto;
import com.wolf.workflow.comment.service.CommentService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    /**
     * 댓글 등록
     *
     * @param requestDto 댓글을 등록할 내용을 담는 dto
     */
    @ResponseBody
    @PostMapping("/{cardId}")
    public ResponseEntity<ApiResponse<CommentCreateResponseDto>> createComment(
            @RequestBody CommentCreateRequestDto requestDto,
            @PathVariable Long cardId
    ) {
        CommentCreateResponseDto responseDto = commentService.createComment(requestDto, cardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(responseDto));

    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<CommentCreateResponseDto>>> getComments() {
        List<CommentCreateResponseDto> responseDtoList = commentService.getAllComment();
        return ResponseEntity.ok()
                .body(ApiResponse.of(responseDtoList));
    }
}
