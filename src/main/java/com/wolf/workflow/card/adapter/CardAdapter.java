package com.wolf.workflow.card.adapter;

import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.card.repository.CardRepository;
import com.wolf.workflow.common.exception.NotFoundCardException;
import com.wolf.workflow.common.exception.NotFoundCardListException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
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
                new NotFoundCardException(messageSource.getMessage("not.find.card", null, Locale.getDefault()))
        );
    }

    public List<Card> getAllCards() {
        List<Card> cardList = cardRepository.findAll();
        if (cardList.isEmpty()) {
            throw new NotFoundCardListException(messageSource.getMessage("not.find.cardList", null, Locale.getDefault()));
        }
        return cardList;
    }

    public List<Card> getCardsByAssigneeId(Long assigneeId) {
        List<Card> cardList = cardRepository.findByAssigneeId(assigneeId);
        if (cardList.isEmpty()) {
            throw new NotFoundCardListException(messageSource.getMessage("not.find.cardList", null, Locale.getDefault()));
        }
        return cardList;
    }

    public List<Card> getCardsByColumnId(Long columnId) {
        List<Card> cardList = cardRepository.findByColumnId(columnId);
        if (cardList.isEmpty()) {
            throw new NotFoundCardListException(messageSource.getMessage("not.find.cardList", null, Locale.getDefault()));
        }
        return cardList;
    }
}
