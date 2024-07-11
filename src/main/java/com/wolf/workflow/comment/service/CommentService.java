package com.wolf.workflow.comment.service;

import com.wolf.workflow.card.adapter.CardAdapter;
import com.wolf.workflow.card.entity.Card;
import com.wolf.workflow.comment.adapter.CommentAdapter;
import com.wolf.workflow.comment.dto.request.CommentCreateRequestDto;
import com.wolf.workflow.comment.dto.response.CommentCreateResponseDto;
import com.wolf.workflow.comment.dto.response.CommentGetResponseDto;
import com.wolf.workflow.comment.entity.Comment;
import com.wolf.workflow.common.exception.NotFoundCardException;
import com.wolf.workflow.common.exception.NotFoundUserException;
import com.wolf.workflow.user.adapter.UserAdapter;
import com.wolf.workflow.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentAdapter commentAdapter;
    private final CardAdapter cardAdapter;
    private final UserAdapter userAdapter;

    /**
     * 댓글 생성
     *
     * @param requestDto 생성하려는 댓글 내용
     * @param cardId 댓글을 달 카드
     * @throws NotFoundUserException 사용자를 찾을 수 없음
     * @throws NotFoundCardException 카드를 찾을 수 없음
     */
    @Transactional
    public CommentCreateResponseDto createComment(CommentCreateRequestDto requestDto, Long cardId) {

        // 사용자 ID로 검색
        User user = userAdapter.getUserById(requestDto.getUserId());

        // 카드 ID로 검색
        Card card = cardAdapter.getCardById(cardId);

        // 댓글 생성
        Comment comment = Comment.createComment(requestDto, card, user);
        commentAdapter.createComment(comment);

        return CommentCreateResponseDto.of(comment);
    }

    /**
     * 전체 댓글 조회
     *
     * @param cardId 댓글 조회할 카드
     * @throws NotFoundCardException 카드를 찾을 수 없음
     */
    public List<CommentGetResponseDto> getAllComments(Long cardId) {

        // 카드 ID로 검색
        Card card = cardAdapter.getCardById(cardId);

        // 댓글 전체 조회
        List<Comment> commentList = commentAdapter.getAllCommentsByCardId(cardId);
        return commentList.stream().map(CommentGetResponseDto::new).toList();
    }
}
