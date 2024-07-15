package com.wolf.workflow.board.adapter;

import com.wolf.workflow.board.entity.Board;
import com.wolf.workflow.board.entity.BoardUserRole;
import com.wolf.workflow.board.repository.BoardRepository;
import com.wolf.workflow.common.exception.NotFoundCardException;
import com.wolf.workflow.common.exception.UnauthorizedUserException;
import com.wolf.workflow.common.security.AuthenticationUser;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardAdapter {

    private final BoardRepository boardRepository;


    public void createBoard(Board board) {
        boardRepository.save(board);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() ->
            new NotFoundCardException(MessageUtil.getMessage("not.find.board")));
    }
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
    public Page<Board> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public boolean isManager(AuthenticationUser user) {
        return user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(auth -> auth.equals("ROLE_" + BoardUserRole.GENERAL_MANAGER.name()));
    }

    public void checkManagerAuthority(AuthenticationUser user) {
        if (!isManager(user)) {
            throw new UnauthorizedUserException(MessageUtil.getMessage("unauthorized.board"));
        }
    }
}