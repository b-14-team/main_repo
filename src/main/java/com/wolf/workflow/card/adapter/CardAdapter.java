package com.wolf.workflow.card.adapter;

import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.card.repository.CardRepository;
import com.wolf.workflow.column.entity.Columns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardAdapter {
    private final CardRepository cardRepository;

    public void createCard(Card card) {
        cardRepository.save(card);
    }
}
