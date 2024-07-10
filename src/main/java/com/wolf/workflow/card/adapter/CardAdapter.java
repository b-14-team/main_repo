package com.wolf.workflow.card.adapter;

import com.wolf.workflow.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardAdapter {
    private final CardRepository cardRepository;
}
