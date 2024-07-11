package com.wolf.workflow.board.adapter;

import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.board.repository.BoardUserRepository;
import com.wolf.workflow.common.exception.NotFoundBoardUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BoardUserAdapter {
    private final BoardUserRepository boardUserRepository;
    private final MessageSource messageSource;

    public BoardUser getBoardByUserById(Long id) {
       return boardUserRepository.findById(id).orElseThrow(()->
               new NotFoundBoardUserException(messageSource.getMessage("not.find.boardUser",null, Locale.getDefault()))
       );
    }
}
