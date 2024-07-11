package com.wolf.workflow.comment.service;

import com.wolf.workflow.comment.dto.request.CommentRequestDto;
import com.wolf.workflow.comment.dto.response.CommentResponseDto;
import com.wolf.workflow.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {

        return new CommentResponseDto();
    }

    public List<CommentResponseDto> getAllComment() {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        return commentResponseDtoList;
    }
}
