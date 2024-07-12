package com.wolf.workflow.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private Long userId;    // Security 전 임시 변수
    private String content;
}
