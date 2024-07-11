package com.wolf.workflow.card.service;

import ch.qos.logback.core.util.StringUtil;
import com.wolf.workflow.board.adapter.BoardUserAdapter;
import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.card.adapter.CardAdapter;
import com.wolf.workflow.card.dto.request.CardRequestDto;
import com.wolf.workflow.card.dto.request.CardUpdateRequestDto;
import com.wolf.workflow.card.dto.response.CardResponseDto;
import com.wolf.workflow.card.dto.response.CardUpdateResponseDto;
import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.column.adapter.ColumnAdapter;
import com.wolf.workflow.column.entity.Columns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Objects;

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

        // assigneeId가 있는 경우
        if (Objects.nonNull(requestDto.getAssigneeId())) {
            // assigneeId로 보드유저 찾아오기
            BoardUser boardUser = boardUserAdapter.getBoardByUserById(requestDto.getAssigneeId());
            //카드 생성후 저장
            Card card = Card.createCard(requestDto, columns);
            cardAdapter.createCard(card);
            //ResponseDto 생성
            return CardResponseDto.of(card, columns, boardUser.getUser().getNickName());
        }

        // assigneeId가 없는 경우
        Card card = Card.createCard(requestDto, columns);
        cardAdapter.createCard(card);

       return CardResponseDto.of(card, columns, null);
    }

    /**
     * 카드 업데이트
     * @param requestDto
     * @param cardId
     * @return CardUpdateResponseDto
     * @throws "카드아이디로 카드를 찾을 수 없을 때"
     * @throws "보드유저를 찾을 수 없을 때"
     */
    @Transactional
    public CardUpdateResponseDto updateCard(CardUpdateRequestDto requestDto, Long cardId) {

        // cardId로 카드 찾아오기
        Card card = cardAdapter.getCardById(cardId);

        // assigneeId가 있는 경우
        if (Objects.nonNull(requestDto.getAssigneeId())) {
            // assigneeId로 보드유저 찾아오기
            BoardUser boardUser = boardUserAdapter.getBoardByUserById(requestDto.getAssigneeId());
            // 업데이트
            card.updateCard(requestDto.getTitle(),requestDto.getContent(),requestDto.getAssigneeId(),requestDto.getDeadDate());
            //ResponseDto 생성
            return CardUpdateResponseDto.of(card, card.getColumns(), boardUser.getUser().getNickName());
        }

        // 업데이트
        card.updateCard(requestDto.getTitle(),requestDto.getContent(),
                requestDto.getAssigneeId(),requestDto.getDeadDate());

        return CardUpdateResponseDto.of(card,card.getColumns(),null);
    }

}
