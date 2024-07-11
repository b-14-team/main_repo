package com.wolf.workflow.comment.dto.response;

import com.wolf.workflow.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentGetResponseDto {
    private Long commentId;
    private Long cardId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentGetResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.cardId = comment.getCard().getId();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
