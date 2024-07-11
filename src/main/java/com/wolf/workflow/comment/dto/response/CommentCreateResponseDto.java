package com.wolf.workflow.comment.dto.response;

import com.wolf.workflow.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentCreateResponseDto {
    private Long commentId;
    private Long cardId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentCreateResponseDto of(Comment comment) {
        return CommentCreateResponseDto.builder()
                .commentId(comment.getId())
                .cardId(comment.getCard().getId())
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
