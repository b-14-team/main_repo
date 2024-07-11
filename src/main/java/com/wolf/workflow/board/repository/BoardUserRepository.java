package com.wolf.workflow.board.repository;

import com.wolf.workflow.board.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser,Long> {
}
