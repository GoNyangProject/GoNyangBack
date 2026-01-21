package com.example.tossback.board.comment.service;

import com.example.tossback.board.comment.dto.CommentRequestDTO;
import com.example.tossback.board.comment.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO);

    List<CommentResponseDTO> getComment(long boardId);

}
