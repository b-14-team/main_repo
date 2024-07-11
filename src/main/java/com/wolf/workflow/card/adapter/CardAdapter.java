package com.wolf.workflow.card.adapter;

import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.card.repository.CardRepository;
import com.wolf.workflow.common.exception.NotFoundCardException;
import com.wolf.workflow.common.exception.NotFoundCardListException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CardAdapter {

    private final CardRepository cardRepository;

    public void createCard(Card card) {
        cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {

        return cardRepository.findById(cardId).orElseThrow(() ->
                new NotFoundCardException(MessageUtil.getMessage("not.find.card"))
        );
    }

    public List<Card> getAllCards() {
        List<Card> cardList = cardRepository.findAll();
        if (cardList.isEmpty()) {
            throw new NotFoundCardListException(MessageUtil.getMessage("not.find.cardList"));
        }
        return cardList;
    }

    public List<Card> getCardsByAssigneeId(Long assigneeId) {
        List<Card> cardList = cardRepository.findByAssigneeId(assigneeId);
        if (cardList.isEmpty()) {
            throw new NotFoundCardListException(MessageUtil.getMessage("not.find.cardList"));
        }
        return cardList;
    }

    public List<Card> getCardsByColumnId(Long columnId) {
        List<Card> cardList = cardRepository.findByColumnsId(columnId);
        if (cardList.isEmpty()) {
            throw new NotFoundCardListException(MessageUtil.getMessage("not.find.cardList"));
        }
        return cardList;
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
