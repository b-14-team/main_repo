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
import com.wolf.workflow.common.exception.*;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public CardCreateResponseDto createCard(CardCreateRequestDto requestDto, Long columnId) {

        // requestDto 의 deadDate 가 현재시간보다 이전이면 예외 처리 해주기
        if (Objects.nonNull(requestDto.getDeadDate())) {
            checkDeadDate(requestDto.getDeadDate());
        }

        // columId로 칼럼 찾아오기
        Columns columns = columnAdapter.findColumnsById(columnId);

        // assigneeId가 있는 경우
        if (Objects.nonNull(requestDto.getAssigneeId())) {
            // assigneeId로 보드유저 찾아오기
            BoardUser boardUser = boardUserAdapter.getBoardUserById(requestDto.getAssigneeId());
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

        // requestDto 의 deadDate 가 현재시간보다 이전이면 예외 처리 해주기
        if (Objects.nonNull(requestDto.getDeadDate())) {
            checkDeadDate(requestDto.getDeadDate());
        }

        // cardId로 카드 찾아오기
        Card card = cardAdapter.getCardById(cardId);

        // assigneeId가 있는 경우
        if (Objects.nonNull(requestDto.getAssigneeId())) {
            // assigneeId로 보드유저 찾아오기
            BoardUser boardUser = boardUserAdapter.getBoardUserById(requestDto.getAssigneeId());
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
            BoardUser boardUser = boardUserAdapter.getBoardUserById(card.getAssigneeId());

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
    public List<CardGetAllResponseDto> getAllCards(int page, int size) {

        // 페이징 처리 및 카드 가져오기
        Pageable pageable = PageRequest.of(page, size);
        List<Card> cards = cardAdapter.getAllCards(pageable);
        // 리스폰스 List 생성
        List<CardGetAllResponseDto> cardGetAllResponseDtoList = new ArrayList<>();

        //assigneeId 리스트 뽑아오기 및 assigneeId로 BoardUser 리스트 가져오기
        List<Long> assigneeIds = cards.stream().filter(c -> c.getAssigneeId() != null)
                .map(Card::getAssigneeId).toList();
        List<BoardUser> boardUsers = boardUserAdapter.getBoardUsersByIds(assigneeIds);

        Map<Long, BoardUser> boardUserMap = boardUsers.stream()
                .collect(Collectors.toMap(bu -> bu.getUser().getId(),
                        bu -> bu));

        for (Card card : cards) {
            Long assigneeId = card.getAssigneeId();
            String nickName = null;
            if (boardUserMap.containsKey(assigneeId) && assigneeId != null) {
                 nickName = boardUserMap.get(assigneeId).getUser().getNickName();
            }
            cardGetAllResponseDtoList.add(CardGetAllResponseDto.of(card, card.getColumns(), nickName));

        }
        return cardGetAllResponseDtoList;

    }
    // 1. assigneeIds 를 가지고 보드유저 리스트를 가져오는 방법찾기
    // 2. 카드리스트에서 각 assignee의 유저정보를 가져오려면 보드유저 리스트를 어떤 형태로 구현해야 추가 DB 조회없이 가져올 수 있을까?
    // 3. 카드아답터.getAllCards 바꾸기 -> 페이징 처리하기


    /**
     * 담당자별 카드조회
     *
     * @param assigneeId
     * @return List<CardsGetByAssigneeId>
     * @throws NotFoundCardListException  조회할 카드가 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     */
    public List<CardsGetByAssigneeId> getCardsByAssigneeId(Long assigneeId) {

        List<Card> cards = cardAdapter.getCardsByAssigneeId(assigneeId);
        List<CardsGetByAssigneeId> cardsGetByAssigneeIdList = new ArrayList<>();

        //assigneeId 리스트 뽑아오기 및 assigneeId로 BoardUser 리스트 가져오기
        List<Long> assigneeIds = cards.stream().filter(c -> c.getAssigneeId() != null)
                .map(Card::getAssigneeId).toList();
        List<BoardUser> boardUsers = boardUserAdapter.getBoardUsersByIds(assigneeIds);

        Map<Long, BoardUser> boardUserMap = boardUsers.stream()
                .collect(Collectors.toMap(bu -> bu.getUser().getId(),
                        bu -> bu));


        for (Card card : cards) {
            String nickName = boardUserMap.get(assigneeId).getUser().getNickName();
            cardsGetByAssigneeIdList.add(CardsGetByAssigneeId.of(card, card.getColumns(), nickName));

        }
        return cardsGetByAssigneeIdList;
    }

    /**
     * 상태별 카드 조회
     *
     * @param columnId
     * @return List<CardsGetByColumnId>
     * @throws NotFoundCardListException  조회할 카드가 없을 때
     * @throws NotFoundBoardUserException assigneeId가 있는데 보드유저를 찾을 수 없을 때
     * @throws NotFoundColumnException    columnId에 맞는 column이 없을 때
     */
    public List<CardsGetByColumnId> getCardsByColumnId(Long columnId) {

        List<Card> cards = cardAdapter.getCardsByColumnId(columnId);
        List<CardsGetByColumnId> cardsGetByColumnIdList = new ArrayList<>();

        //assigneeId 리스트 뽑아오기 및 assigneeId로 BoardUser 리스트 가져오기
        List<Long> assigneeIds = cards.stream().filter(c -> c.getAssigneeId() != null)
                .map(Card::getAssigneeId).toList();
        List<BoardUser> boardUsers = boardUserAdapter.getBoardUsersByIds(assigneeIds);

        Map<Long, BoardUser> boardUserMap = boardUsers.stream()
                .collect(Collectors.toMap(bu -> bu.getUser().getId(),
                        bu -> bu));


        for (Card card : cards) {
            Long assigneeId = card.getAssigneeId();
            String nickName = null;
            if (boardUserMap.containsKey(assigneeId) && assigneeId != null) {
                nickName = boardUserMap.get(assigneeId).getUser().getNickName();
            }
            cardsGetByColumnIdList.add(CardsGetByColumnId.of(card, card.getColumns(), nickName));

        }

        return cardsGetByColumnIdList;
    }

    /**
     * 카드 삭제하기
     *
     * @param cardId
     * @return String cardId + " 번 카드 삭제 완료 되었습니다.";
     * @throws NotFoundCardException 카드아이디로 카드를 찾을 수 없을 때
     */
    @Transactional
    public String deleteCard(Long cardId) {

        // cardId로 카드 찾아오기
        Card card = cardAdapter.getCardById(cardId);
        // 삭제 하기
        cardAdapter.deleteCard(card.getId());

        return cardId + " 번 카드 삭제 완료 되었습니다.";
    }

    /**
     * 카드 이동하기
     *
     * @param cardId
     * @param columnId
     * @return cardId + " 번 카드아이디 " + columnId + " 번 칼럼으로 이동";
     * @throws NotFoundColumnException 칼럼아이디로 칼럼을 찾을 수 없을 때
     * @throws NotFoundCardException 카드아이디로 카드를 찾을 수 없을 때
     */
    @Transactional
    public String moveCard(Long cardId, Long columnId) {

        //cardId로 카드 찾아오기
        Card card = cardAdapter.getCardById(cardId);
        // columId로 칼럼 찾아오기
        Columns columns = columnAdapter.findColumnsById(columnId);

        card.updateMoveCard(columns);

        return cardId + " 번 카드아이디 " + columnId + " 번 칼럼으로 이동";
    }

    private void checkDeadDate(LocalDate deadDate) {
        if (deadDate.isBefore(LocalDate.now())) {
            throw new InvalidDeadDateException(MessageUtil.getMessage("invalid.deadDate"));
        }
    }



}