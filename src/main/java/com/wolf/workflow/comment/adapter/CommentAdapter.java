package com.wolf.workflow.comment.adapter;

import com.wolf.workflow.comment.entity.Comment;
import com.wolf.workflow.comment.repository.CommentRepository;
import com.wolf.workflow.common.exception.NotFoundCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CommentAdapter {

    private final CommentRepository commentRepository;
    private final MessageSource messageSource;

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> getAllCommentsByCardId(Long cardId) {
        return commentRepository.findAllByCardId(cardId);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException(
                        messageSource.getMessage(
                                "not.find.comment",
                                null,
                                Locale.getDefault()))
        );
    }

    public void existsById(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundCommentException((
                    messageSource.getMessage(
                            "not.find.comment",
                            null,
                            Locale.getDefault()))
            );
        }
    }
}
