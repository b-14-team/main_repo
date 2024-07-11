package com.wolf.workflow.comment.adapter;

import com.wolf.workflow.comment.entity.Comment;
import com.wolf.workflow.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAdapter {

    private final CommentRepository commentRepository;

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }
}
