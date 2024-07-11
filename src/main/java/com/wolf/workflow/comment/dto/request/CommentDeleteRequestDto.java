package com.wolf.workflow.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteRequestDto {
    private Long userId;  // Security 전 임시 id
}
