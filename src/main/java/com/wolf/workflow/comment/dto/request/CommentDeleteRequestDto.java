package com.wolf.workflow.comment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentDeleteRequestDto {
    private final Long userId;  // Security 전 임시 id
}
