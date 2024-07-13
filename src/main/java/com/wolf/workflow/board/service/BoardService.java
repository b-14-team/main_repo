package com.wolf.workflow.board.service;

import com.wolf.workflow.board.adapter.BoardAdapter;
import com.wolf.workflow.board.adapter.BoardUserAdapter;
import com.wolf.workflow.board.dto.request.BoardRequestDto;
import com.wolf.workflow.board.dto.request.BoardUpdateRequestDto;
import com.wolf.workflow.board.dto.response.BoardGetResponseDto;
import com.wolf.workflow.board.dto.response.BoardResponseDto;
import com.wolf.workflow.board.dto.response.BoardUpdateResponseDto;
import com.wolf.workflow.board.entity.Board;
import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.board.entity.BoardUserRole;
import com.wolf.workflow.board.entity.InvitationStatus;
import com.wolf.workflow.board.entity.Participation;
import com.wolf.workflow.common.exception.NotFoundBoardException;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardAdapter boardAdapter;
  private final BoardUserAdapter boardUserAdapter;
  private final UserAdapter userAdapter;



  /**
   * 보드 생성하기
   *
   * @param requestDto
   * @throws NotFoundBoardException 보드를 찾을 수 없는 경우
   */

  public BoardResponseDto createBoard(BoardRequestDto requestDto, Long userId) {
    User user = userAdapter.getUserById(requestDto.getUserId());
    Board board = Board.createBoard(requestDto, user);
    boardAdapter.createBoard(board);

    return BoardResponseDto.of(board);
  }

  /**
   * 보드 수정
   *
   * @param requestDto
   * @param boardId
   * @throws NotFoundBoardException 보드를 찾을 수 없는 경우
   */

  public BoardUpdateResponseDto updateBoard(BoardUpdateRequestDto requestDto, Long boardId, Long userId) {
    User user = userAdapter.getUserById(userId);
    // 보드 존재 여부 체크
    Board board = boardAdapter.getBoardById(boardId);
    board.updateBoard(requestDto.getBoard_name(), requestDto.getContent(), user.getId());

    return BoardUpdateResponseDto.of(board);
  }

  /**
   * 보드 삭제
   *
   * @param boardId
   * @throws NotFoundBoardException 보드를 찾을 수 없는 경우
   */
  public void deleteBoard(Long boardId) {
    // 보드 존재 여부 체크
    boardAdapter.getBoardById(boardId);
    // 보드 삭제
    boardAdapter.deleteBoard(boardId);
  }

  /**
   * 보드 조회
   *
   * @param boardId
   * @throws NotFoundBoardException 보드를 찾을 수 없는 경우
   */

  public BoardGetResponseDto getBoardById(Long boardId) {
    Board board = boardAdapter.getBoardById(boardId);
    return BoardGetResponseDto.of(board);
  }

  /**
   * 전체 보드 조회
   *
   * @param page   페이지 번호
   * @param size   페이지 크기
   * @param sortBy 정렬 기준 필드
   * @param isAsc  오름차순 여부
   * @throws NotFoundBoardException 보드를 찾을 수 없는 경우
   */
  @Transactional(readOnly = true)
  public Page<BoardGetResponseDto> getAllBoards(
      int page, int size, String sortBy, boolean isAsc) {

    // 정렬 세팅
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    // 보드 전체 조회 페이지
    Page<Board> boardPage = boardAdapter.getAllBoards(pageable);
    return boardPage.map(BoardGetResponseDto::of);
  }

  /**
   * 보드 초대
   *
   * @param boardId
   * @param userId
   * @throws NotFoundBoardException 보드를 찾을 수 없는 경우
   */

  public void inviteUserToBoard(Long boardId, Long userId) {
    // 초대하려는 보드 존재 여부 체크
    Board board = boardAdapter.getBoardById(boardId);

    // 초대하려는 사용자 존재 여부 체크
    User user = userAdapter.getUserById(userId);
    // 초대하려는 사용자가 이미 보드에 초대된 경우 체크
    boardUserAdapter.BoardUserExists(boardId, userId);

    BoardUser newBoardUser = BoardUser.createBoardUser(
        board, user, Participation.ENABLE, BoardUserRole.ASSIGNEE, InvitationStatus.ACCEPTED
    );
    boardUserAdapter.saveBoardUser(newBoardUser);
  }

  /**
   * 보드 승인
   *
   * @param boardId
   * @param userId
   * @param approve
   */
  @Transactional
  public void approveInvitation(Long boardId, Long userId, boolean approve) {
    boardUserAdapter.approveInvitation(boardId, userId, approve);
  }
}

