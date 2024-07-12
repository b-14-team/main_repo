package com.wolf.workflow.board.adapter;

import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.board.entity.Participation;
import com.wolf.workflow.board.repository.BoardUserRepository;
import com.wolf.workflow.common.exception.NotFoundBoardUserException;
import com.wolf.workflow.common.exception.AlreadyInviteException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardUserAdapter {
    private final BoardUserRepository boardUserRepository;
    private final MessageSource messageSource;

    public BoardUser getBoardByUserById(Long id) {
       return boardUserRepository.findById(id).orElseThrow(()->
               new NotFoundBoardUserException(MessageUtil.getMessage("not.find.boardUser"))
       );
    }
    public BoardUser getBoardUser(Long boardId, Long userId) {
     return boardUserRepository.findByBoard_IdAndUser_Id(boardId, userId);
  }

    public BoardUser saveBoardUser(BoardUser boardUser) {
      return boardUserRepository.save(boardUser);
  }
  public void BoardUserExists(Long boardId, Long userId) {
    BoardUser existsBoardUser = getBoardUser(boardId, userId);
    if (existsBoardUser != null && existsBoardUser.getParticipation() == Participation.ENABLE) {
      throw new AlreadyInviteException("해당 사용자는 이미 보드에 초대되었습니다.");
    }
  }

  public void approveInvitation(Long boardId, Long userId, boolean approved) {
    BoardUser boardUser = getBoardUser(boardId, userId);
    boardUser.updateInvitationStatus(approved);

    saveBoardUser(boardUser);
  }
}
