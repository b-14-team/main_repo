package com.wolf.workflow.board.adapter;

import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.board.repository.BoardUserRepository;
import com.wolf.workflow.common.exception.NotFoundBoardUserException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardUserAdapter {
    private final BoardUserRepository boardUserRepository;

    public BoardUser getBoardUserById(Long id) {
       return boardUserRepository.findById(id).orElseThrow(()->
               new NotFoundBoardUserException(MessageUtil.getMessage("not.find.boardUser"))
       );
    }
}
