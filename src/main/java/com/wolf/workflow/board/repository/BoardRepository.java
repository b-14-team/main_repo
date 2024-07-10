package com.wolf.workflow.board.repository;

import com.wolf.workflow.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
