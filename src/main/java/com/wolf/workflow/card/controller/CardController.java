package com.wolf.workflow.card.controller;

import com.wolf.workflow.card.dto.request.CardRequestDto;
import com.wolf.workflow.card.dto.request.CardUpdateRequestDto;
import com.wolf.workflow.card.dto.response.CardResponseDto;
import com.wolf.workflow.card.dto.response.CardUpdateResponseDto;
import com.wolf.workflow.card.service.CardService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
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
    @PostMapping("/{columnId}")
    public ApiResponse<CardResponseDto> createCard(@RequestBody CardRequestDto requestDto, @PathVariable Long columnId) {

        CardResponseDto cardResponseDto = cardService.createCard(requestDto,columnId);

        return ApiResponse.of(cardResponseDto);
    }

    /**
     * 카드 업데이트
     * @param requestDto
     * @param cardId
     * @return CardUpdateResponseDto
     *
     */
    @PatchMapping("/{cardId}")
    public ApiResponse<CardUpdateResponseDto> updateCard(@RequestBody CardUpdateRequestDto requestDto, @PathVariable Long cardId) {

        CardUpdateResponseDto cardUpdateResponseDto = cardService.updateCard(requestDto,cardId);

        return ApiResponse.of(cardUpdateResponseDto);

    }



}
