package com.wolf.workflow.card.controller;

import com.wolf.workflow.card.dto.request.CardRequestDto;
import com.wolf.workflow.card.dto.request.CardUpdateRequestDto;
import com.wolf.workflow.card.dto.response.CardCreateResponseDto;
import com.wolf.workflow.card.dto.response.CardUpdateResponseDto;
import com.wolf.workflow.card.dto.response.CardGetResponseDto;
import com.wolf.workflow.card.service.CardService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    /**
     * 카드 생성
     * @param requestDto
     * @param columnId
     * @return CardResponseDto
     */
    @ResponseBody
    @PostMapping("/{columnId}")
    public ResponseEntity<ApiResponse<CardCreateResponseDto>> createCard(@Valid @RequestBody CardRequestDto requestDto, @PathVariable Long columnId) {

        CardCreateResponseDto cardCreateResponseDto = cardService.createCard(requestDto, columnId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(cardCreateResponseDto));
    }

    /**
     * 카드 업데이트
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
}
