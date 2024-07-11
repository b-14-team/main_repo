package com.wolf.workflow.card.service;

import com.wolf.workflow.board.adapter.BoardUserAdapter;
import com.wolf.workflow.board.entity.BoardUser;
import com.wolf.workflow.card.adapter.CardAdapter;
import com.wolf.workflow.card.dto.request.CardCreateRequestDto;
import com.wolf.workflow.card.dto.request.CardUpdateRequestDto;
import com.wolf.workflow.card.dto.response.*;
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
     * @throws NotFoundColumnException columnId에 맞는 column이 없을 때
     * @throws NotFoundBoardUserException assigneeId 에 맞는 보드유저가 없을 때
     */

    public CardCreateResponseDto createCard(CardCreateRequestDto requestDto, Long columnId) {

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
     * @throws NotFoundCardException 카드아이디로 카드를 찾을 수 없을 때
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
     * @throws NotFoundCardException 카드아이디로 카드를 찾을 수 없을 때
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
     * @throws NotFoundCardListException  조회할 카드가 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     */
    public List<CardGetAllResponseDto> getAllCards() {

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

    /**
     * 담당자별 카드조회
     *
     * @param assigneeId
     * @return List<CardsGetByAssigneeId>
     * @throws NotFoundCardListException 조회할 카드가 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     */
    public List<CardsGetByAssigneeId> getCardsByAssigneeId(Long assigneeId) {

        List<Card> cards = cardAdapter.getCardsByAssigneeId(assigneeId);
        List<CardsGetByAssigneeId> cardsGetByAssigneeIdList = new ArrayList<>();

        // assigneeId가 있는 카드의 경우만 조회하면 됨
        for (Card card : cards) {
            // assigneeId가 있는 카드의 경우
            if (Objects.nonNull(card.getAssigneeId())) {
                BoardUser boardUser = boardUserAdapter.getBoardByUserById(card.getAssigneeId());
                cardsGetByAssigneeIdList.add(CardsGetByAssigneeId.of(card, card.getColumns(), boardUser.getUser().getNickName()));
            }
        }

        return cardsGetByAssigneeIdList;
    }

    /**
     * 상태별 카드 조회
     * @param columnId
     * @return List<CardsGetByColumnId>
     * @throws NotFoundCardListException 조회할 카드가 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     * @throws NotFoundColumnException    columnId에 맞는 column이 없을 때
     */
    public List<CardsGetByColumnId> getCardsByColumnId(Long columnId) {

        List<Card> cards = cardAdapter.getCardsByColumnId(columnId);
        List<CardsGetByColumnId> cardsGetByColumnIdList = new ArrayList<>();

        for (Card card : cards) {
            // assigneeId가 있는 카드의 경우
            if (Objects.nonNull(card.getAssigneeId())) {
                BoardUser boardUser = boardUserAdapter.getBoardByUserById(card.getAssigneeId());
                cardsGetByColumnIdList.add(CardsGetByColumnId.of(card, card.getColumns(), boardUser.getUser().getNickName()));
            } else {
                // assigneeId가 없는 카드의 경우
                cardsGetByColumnIdList.add(CardsGetByColumnId.of(card, card.getColumns(), null));
            }
        }

        return cardsGetByColumnIdList;
    }

    /**
     * 카드 삭제하기
     *
     * @param cardId
     * @return String cardId + " 번 카드 삭제 완료 되었습니다.";
     @throws NotFoundCardException 카드아이디로 카드를 찾을 수 없을 때
     */
    @Transactional
    public String deleteCard(Long cardId) {

        // cardId로 카드 찾아오기
        Card card = cardAdapter.getCardById(cardId);
        // 삭제 하기
        cardAdapter.deleteCard(card.getId());

        return cardId + " 번 카드 삭제 완료 되었습니다.";
    }
}