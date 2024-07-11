package com.wolf.workflow.comment.entity;

import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.comment.dto.request.CommentCreateRequestDto;
import com.wolf.workflow.common.Timestamped;
import com.wolf.workflow.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Comment(Card card, User user, String content) {
        this.card = card;
        this.user = user;
        this.content = content;
    }

    public static Comment createComment(CommentCreateRequestDto requestDto, Card card, User user) {
        return Comment.builder()
                .content(requestDto.getContent())
                .card(card)
                .user(user)
                .build();
    }
}
