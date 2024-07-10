package com.wolf.workflow.board.adapter;

import com.wolf.workflow.board.repository.BoardRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardAdapter {

    private final BoardRepository boardRepository;
}
