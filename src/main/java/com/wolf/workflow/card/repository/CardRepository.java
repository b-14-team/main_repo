package com.wolf.workflow.card.repository;

import com.wolf.workflow.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {
}
