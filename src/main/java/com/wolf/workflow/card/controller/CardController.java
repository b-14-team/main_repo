package com.wolf.workflow.card.controller;

import com.wolf.workflow.card.dto.request.CardCreateRequestDto;
import com.wolf.workflow.card.dto.request.CardUpdateRequestDto;
import com.wolf.workflow.card.dto.response.*;
import com.wolf.workflow.card.service.CardService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    /**
     * 카드 생성
     *
     * @param requestDto
     * @param columnId
     * @return CardResponseDto
     */
    @ResponseBody
    @PostMapping("/{columnId}")
    public ResponseEntity<ApiResponse<CardCreateResponseDto>> createCard(@Valid @RequestBody CardCreateRequestDto requestDto, @PathVariable Long columnId) {

        CardCreateResponseDto cardCreateResponseDto = cardService.createCard(requestDto, columnId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardCreateResponseDto));
    }

    /**
     * 카드 업데이트
     *
     * @param requestDto
     * @param cardId
     * @return CardUpdateResponseDto
     */
    @ResponseBody
    @PatchMapping("/{cardId}")
    public ResponseEntity<ApiResponse<CardUpdateResponseDto>> updateCard(@Valid @RequestBody CardUpdateRequestDto requestDto, @PathVariable Long cardId) {

        CardUpdateResponseDto cardUpdateResponseDto = cardService.updateCard(requestDto, cardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardUpdateResponseDto));

    }

    /**
     * 카드 단일조회
     *
     * @param cardId
     * @return CardGetResponseDto
     */
    @ResponseBody
    @GetMapping("/{cardId}")
    public ResponseEntity<ApiResponse<CardGetResponseDto>> getCard(@PathVariable Long cardId) {

        CardGetResponseDto cardGetResponseDto = cardService.getCard(cardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardGetResponseDto));
    }

    /**
     * 모든 카드 조회
     *
     * @return List<CardGetResponseDto>
     */
    @ResponseBody
    @GetMapping
    public ResponseEntity<ApiResponse<List<CardGetAllResponseDto>>> getAllCards() {

        List<CardGetAllResponseDto> cardGetAllResponseDto = cardService.getAllCards();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardGetAllResponseDto));
    }

    /**
     * 담당자별 카드 조회
     *
     * @param assigneeId
     * @return List<CardsGetByAssigneeId>
     */
    @ResponseBody
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<ApiResponse<List<CardsGetByAssigneeId>>> getCardsByAssigneeId(@PathVariable Long assigneeId) {

        List<CardsGetByAssigneeId> cardsGetByAssigneeIdList = cardService.getCardsByAssigneeId(assigneeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardsGetByAssigneeIdList));
    }

    /**
     * 상태별 카드 조회
     *
     * @param columnId
     * @return List<CardsGetByColumnId>
     */
    @ResponseBody
    @GetMapping("/columns/{columnId}")
    public ResponseEntity<ApiResponse<List<CardsGetByColumnId>>> getCardsByColumId(@PathVariable Long columnId) {

        List<CardsGetByColumnId> cardsGetByColumnIdList = cardService.getCardsByColumnId(columnId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardsGetByColumnIdList));
    }

    /**
     *
     * @param cardId
     * @return msg
     */
    @ResponseBody
    @DeleteMapping("/{cardId}")
    public ResponseEntity<ApiResponse<String>> deleteCard(@PathVariable Long cardId) {
        String msg = cardService.deleteCard(cardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(msg));

    }

}
