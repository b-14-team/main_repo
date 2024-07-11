package com.wolf.workflow.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentGetRequestDto {
    private int page;
    private int size;
    private String sortBy;
    private boolean isAsc;
}
