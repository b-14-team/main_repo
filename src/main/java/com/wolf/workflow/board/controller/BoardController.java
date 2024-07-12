package com.wolf.workflow.board.controller;

import com.wolf.workflow.board.adapter.BoardUserAdapter;
import com.wolf.workflow.board.dto.request.BoardRequestDto;
import com.wolf.workflow.board.dto.request.BoardUpdateRequestDto;
import com.wolf.workflow.board.dto.response.BoardResponseDto;
import com.wolf.workflow.board.service.BoardService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import com.wolf.workflow.common.util.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

  private final BoardService boardService;
  private final MessageUtil messageUtil;
  private final BoardUserAdapter boardUserAdapter;

  /**
   * 보드 생성
   *
   * @param requestDto
   * @return ResponseEntity<ApiResponse < BoardResponseDto>>
   */
  @ResponseBody
  @PostMapping
  public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(
      @Valid @RequestBody BoardRequestDto requestDto) {
    BoardResponseDto boardResponseDto = boardService.createBoard(
        requestDto,
        requestDto.getUserId());
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of(boardResponseDto));
  }

  /**
   * 보드 수정
   *
   * @param boardId    수정할 보드의 ID
   * @param requestDto 요청 DTO
   * @return 수정된 보드 정보
   */
  @ResponseBody
  @PatchMapping("/{boardId}")
  public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(
      @PathVariable Long boardId,
      @Valid @RequestBody BoardUpdateRequestDto requestDto) {
    BoardResponseDto boardResponseDto = boardService.updateBoard(
        requestDto,
        boardId,
        requestDto.getUserId());
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of(boardResponseDto));
  }

  /**
   * 보드 삭제
   *
   * @param boardId 삭제할 보드의 ID
   * @return 삭제 완료 메시지
   */
  @ResponseBody
  @DeleteMapping("/{boardId}")
  public ResponseEntity<ApiResponse<String>> deleteBoard(@PathVariable Long boardId) {
    boardService.deleteBoard(boardId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of("보드가 삭제되었습니다"));
  }

  /**
   * 보드 조회
   *
   * @param boardId 조회할 보드의 ID
   * @return  조회된 보드 정보
   */
  @ResponseBody
  @GetMapping("/{boardId}")
  public ResponseEntity<ApiResponse<BoardResponseDto>> getBoardById(@PathVariable Long boardId) {
    BoardResponseDto boardResponseDto = boardService.getBoardById(boardId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of(boardResponseDto));
  }

  /**
   * 전체 보드 조회
   *
   * @param page   페이지 번호
   * @param size   페이지 크기
   * @param sortBy 정렬 기준 필드
   * @param isAsc  오름차순 여부사용자 ID
   * @return 전체 보드 목록
   */
  @ResponseBody
  @GetMapping
  public ResponseEntity<ApiResponse<Page<BoardResponseDto>>> getAllBoards(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam( defaultValue = "10") int size,
      @RequestParam( defaultValue = "id") String sortBy,
      @RequestParam( defaultValue = "true") boolean isAsc) {
    Page<BoardResponseDto> boardPage = boardService.getAllBoards(page, size, sortBy, isAsc);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of(boardPage));

  }

  /**
   * 보드에 사용자 초대
   *
   * @param boardId 초대할 보드의 ID
   * @param userId  초대할 사용자 ID
   * @return 초대 완료 메시지
   */
  @ResponseBody
  @PostMapping("/{boardId}/invite/{userId}")
  public ResponseEntity<ApiResponse<String>> inviteUserToBoard(
      @PathVariable Long boardId,
      @PathVariable Long userId
  ) {
    boardService.inviteUserToBoard(boardId, userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of("사용자를 초대하였습니다."));
  }

  /**
   * 사용자 초대 승인 또는 거부
   *
   * @param boardId 보드 ID
   * @param userId  사용자 ID
   * @param approve 승인 여부 (true: 승인, false: 거부)
   * @return 초대 승인/거부 메시지
   */
  @ResponseBody
  @PostMapping("/{boardId}/invite/{userId}/status")
  public ResponseEntity<ApiResponse<String>> approveInvitation(
      @PathVariable Long boardId,
      @PathVariable Long userId,
      @RequestParam boolean approve) {
    boardUserAdapter.approveInvitation(boardId, userId, approve);
    String message = approve ? "초대 승인되었습니다." : "초대 거절되었습니다.";

    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.of(message));
  }
}
