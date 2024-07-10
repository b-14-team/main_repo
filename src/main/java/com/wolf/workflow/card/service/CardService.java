package com.wolf.workflow.card.service;

import com.wolf.workflow.board.adapter.BoardUserAdapter;
import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.card.adapter.CardAdapter;
import com.wolf.workflow.card.dto.request.CardRequestDto;
import com.wolf.workflow.card.dto.response.CardResponseDto;
import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.column.adapter.ColumnAdapter;
import com.wolf.workflow.column.entity.Columns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final BoardUserAdapter boardUserAdapter;
    private final ColumnAdapter columnAdapter;

    /**
     * 카드 생성하기
     * @param requestDto
     * @param columnId
     * @return cardResponseDto
     * @throws "columnId 가 없을때"
     * @throws "assigneeId 에 맞는 보드유저가 없을 때"
     */
    public CardResponseDto createCard(CardRequestDto requestDto, Long columnId) {

        // columId로 칼럼 찾아오기
        Columns columns = columnAdapter.findColumnsById(columnId);

        // assigneeId로 보드유저 찾아오기
        BoardUser boardUser = boardUserAdapter.findBoardUserById(requestDto.getAssigneeId());

        //카드 생성후 저장
        Card card = Card.saveCard(requestDto,columns);
        cardAdapter.createCard(card);

        //ResponseDto 생성
        return CardResponseDto.of(card,columns,boardUser.getUser().getNickName());

    }
}
