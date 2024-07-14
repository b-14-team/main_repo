package com.wolf.workflow.board.repository;

import com.wolf.workflow.board.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {

  List<BoardUser> findAllByIdIn(List<Long> ids);

  BoardUser findByBoardIdAndUserId(Long boardId, Long userId);
}
