package com.wolf.workflow.card.repository;

import com.wolf.workflow.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Long> {
    List<Card> findByAssigneeId(Long assigneeId);

    List<Card> findByColumnsId(Long columnId);

}
