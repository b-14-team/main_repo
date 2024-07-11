package com.wolf.workflow.comment.controller;

import com.wolf.workflow.comment.dto.request.CommentCreateRequestDto;
import com.wolf.workflow.comment.dto.request.CommentUpdateRequestDto;
import com.wolf.workflow.comment.dto.response.CommentCreateResponseDto;
import com.wolf.workflow.comment.dto.response.CommentGetResponseDto;
import com.wolf.workflow.comment.dto.response.CommentUpdateResponseDto;
import com.wolf.workflow.comment.service.CommentService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
     * @param requestDto 댓글을 등록할 내용
     * @return 생성된 댓글 id, 사용자 id, 카드 id, 댓글 내용, 작성 시간, 수정 시간
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

    /**
     * 댓글 전체 조회
     *
     * @param cardId 댓글을 조회할 카드
     * @return 카드에 해당하는 모든 댓글 리스트
     */
    @GetMapping("/{cardId}")
    public ResponseEntity<ApiResponse<List<CommentGetResponseDto>>> getAllComment(
            @PathVariable Long cardId
    ) {
        List<CommentGetResponseDto> responseDtoList = commentService.getAllComments(cardId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(responseDtoList));
    }

    /**
     * 댓글 수정
     *
     * @param requestDto 댓글을 수정할 내용
     * @param commentId  수정할 댓글
     * @return 수정된 댓글 id, 사용자 id, 카드 id, 댓글 내용, 작성 시간, 수정 시간
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentUpdateResponseDto>> updateComment(
            @RequestBody CommentUpdateRequestDto requestDto,
            @PathVariable Long commentId
    ) {
        CommentUpdateResponseDto responseDto = commentService.updateComment(requestDto, commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(responseDto));
    }
}
