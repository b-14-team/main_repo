package com.wolf.workflow.card.controller;

import com.wolf.workflow.card.dto.request.CardRequestDto;
import com.wolf.workflow.card.dto.response.CardResponseDto;
import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.card.service.CardService;
import com.wolf.workflow.common.globalresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards/{columnId}")
public class CardController {

    private final CardService cardService;

    /**
     * 카드 생성
     * @param requestDto
     * @param columnId
     * @return CardResponseDto
     */
    @PostMapping
    public ApiResponse<CardResponseDto> createCard(@RequestBody CardRequestDto requestDto, @PathVariable Long columnId) {

        CardResponseDto cardData = cardService.createCard(requestDto,columnId);

        return ApiResponse.of(cardData);
    }

}
