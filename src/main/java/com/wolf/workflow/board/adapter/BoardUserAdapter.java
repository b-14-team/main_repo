package com.wolf.workflow.board.adapter;

import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.board.entity.Participation;
import com.wolf.workflow.board.repository.BoardUserRepository;
import com.wolf.workflow.common.exception.NotFoundBoardUserException;
import com.wolf.workflow.common.exception.AlreadyInviteException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardUserAdapter {
    private final BoardUserRepository boardUserRepository;

    public BoardUser getBoardUserById(Long id) {
       return boardUserRepository.findById(id).orElseThrow(()->
               new NotFoundBoardUserException(MessageUtil.getMessage("not.find.boardUser"))
       );
    }

    public BoardUser saveBoardUser(BoardUser boardUser) {
      return boardUserRepository.save(boardUser);
  }
  public void BoardUserExists(Long boardId, Long userId) {
    BoardUser existsBoardUser = getBoardUser(boardId, userId);
    if (existsBoardUser != null && existsBoardUser.getParticipation() == Participation.ENABLE) {
      throw new AlreadyInviteException(MessageUtil.getMessage("already.invited"));
    }
  }

  public void approveInvitation(Long boardId, Long userId, boolean approved) {
    BoardUser boardUser = getBoardUser(boardId, userId);
    boardUser.updateInvitationStatus(approved);

    saveBoardUser(boardUser);
  }

    public List<BoardUser> getBoardUsersByIds(List<Long> ids) {
        return boardUserRepository.findAllByIdIn(ids);
    }

  private BoardUser getBoardUser(Long boardId, Long userId) {
    return boardUserRepository.findByBoardIdAndUserId(boardId, userId);
  }


    public List<BoardUser> getBoardUsersByBoardId(Long boardId) {
        return boardUserRepository.findByBoardId(boardId);
    }
}
