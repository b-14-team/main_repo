package com.wolf.workflow.comment.adapter;

import com.wolf.workflow.comment.entity.Comment;
import com.wolf.workflow.comment.repository.CommentRepository;
import com.wolf.workflow.common.exception.NotFoundCommentException;
import com.wolf.workflow.common.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAdapter {

    private final CommentRepository commentRepository;

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public Page<Comment> getAllCommentsPagesByCardId(Long cardId, Pageable pageable) {
        return commentRepository.findAllByCardId(cardId, pageable);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException(MessageUtil.getMessage("not.find.comment"))
        );
    }
}
