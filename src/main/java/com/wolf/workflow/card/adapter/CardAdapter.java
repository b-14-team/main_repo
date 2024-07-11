package com.wolf.workflow.card.adapter;

import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.card.repository.CardRepository;
import com.wolf.workflow.common.exception.NotFoundCardException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CardAdapter {

    private final CardRepository cardRepository;
    private final MessageSource messageSource;

    public void createCard(Card card) {
        cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {

        return cardRepository.findById(cardId).orElseThrow(() ->
                new NotFoundCardException(MessageUtil.getMessage("not.find.card"))
        );
    }

    public void existsById(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new NotFoundCardException(MessageUtil.getMessage("not.find.card")
            );
        }
    }
}
