package com.wolf.workflow.card.service;

import com.wolf.workflow.board.adapter.BoardUserAdapter;
import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.card.adapter.CardAdapter;
import com.wolf.workflow.card.dto.request.CardRequestDto;
import com.wolf.workflow.card.dto.request.CardUpdateRequestDto;
import com.wolf.workflow.card.dto.response.CardCreateResponseDto;
import com.wolf.workflow.card.dto.response.CardGetAllResponseDto;
import com.wolf.workflow.card.dto.response.CardGetResponseDto;
import com.wolf.workflow.card.dto.response.CardUpdateResponseDto;
import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.column.adapter.ColumnAdapter;
import com.wolf.workflow.column.entity.Columns;
import com.wolf.workflow.common.exception.NotFoundBoardUserException;
import com.wolf.workflow.common.exception.NotFoundCardException;
import com.wolf.workflow.common.exception.NotFoundCardListException;
import com.wolf.workflow.common.exception.NotFoundColumnException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final BoardUserAdapter boardUserAdapter;
    private final ColumnAdapter columnAdapter;

    /**
     * 카드 생성하기
     *
     * @param requestDto
     * @param columnId
     * @return cardResponseDto
     * @throws NotFoundColumnException    columnId에 맞는 column이 없을 때
     * @throws NotFoundBoardUserException assigneeId 에 맞는 보드유저가 없을 때
     */

    public CardCreateResponseDto createCard(CardRequestDto requestDto, Long columnId) {

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
            return CardCreateResponseDto.of(card, columns, boardUser.getUser().getNickName());
        }

        // assigneeId가 없는 경우
        Card card = Card.createCard(requestDto, columns);
        cardAdapter.createCard(card);

        return CardCreateResponseDto.of(card, columns, null);
    }

    /**
     * 카드 업데이트
     *
     * @param requestDto
     * @param cardId
     * @return CardUpdateResponseDto
     * @throws NotFoundCardException      카드아이디로 카드를 찾을 수 없을 때
     * @throws NotFoundBoardUserException RequestDto에 assigneeId가 있는데 보드유저를 찾을 수 없을 때
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
            card.updateCard(requestDto.getTitle(), requestDto.getContent(), requestDto.getAssigneeId(), requestDto.getDeadDate());
            //ResponseDto 생성
            return CardUpdateResponseDto.of(card, card.getColumns(), boardUser.getUser().getNickName());
        }

        // requestDto 에 assignId가 없을 때
        // 업데이트
        card.updateCard(requestDto.getTitle(), requestDto.getContent(),
                requestDto.getAssigneeId(), requestDto.getDeadDate());

        return CardUpdateResponseDto.of(card, card.getColumns(), null);
    }

    /**
     * 카드 단일조회
     *
     * @param cardId
     * @return CardGetResponseDto
     * @throws NotFoundCardException      카드아이디로 카드를 찾을 수 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     */

    public CardGetResponseDto getCard(Long cardId) {

        // cardId로 카드 찾아오기
        Card card = cardAdapter.getCardById(cardId);

        // assigneeId가 있는 경우
        if (Objects.nonNull(card.getAssigneeId())) {
            // assigneeId로 보드유저 찾아오기
            BoardUser boardUser = boardUserAdapter.getBoardByUserById(card.getAssigneeId());

            //ResponseDto 생성
            return CardGetResponseDto.of(card, card.getColumns(), boardUser.getUser().getNickName());
        }

        // card 에 assignId가 없을 때
        return CardGetResponseDto.of(card, card.getColumns(), null);
    }

    /**
     * 카드 전체 조회
     *
     * @return List<CardGetAllResponseDto>
     * @throws NotFoundCardListException 조회할 카드가 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     */
    public List<CardGetAllResponseDto> getAllCard() {

        List<Card> cards = cardAdapter.getAllCards();
        List<CardGetAllResponseDto> cardGetAllResponseDtoList = new ArrayList<>();

        for (Card card : cards) {
            // assigneeId가 있는 카드의 경우
            if (Objects.nonNull(card.getAssigneeId())) {
                BoardUser boardUser = boardUserAdapter.getBoardByUserById(card.getAssigneeId());
                cardGetAllResponseDtoList.add(CardGetAllResponseDto.of(card, card.getColumns(), boardUser.getUser().getNickName()));
            } else {
                // assigneeId가 없는 카드의 경우
                cardGetAllResponseDtoList.add(CardGetAllResponseDto.of(card, card.getColumns(), null));
            }
        }

        return cardGetAllResponseDtoList;
    }
}
