package com.example.tossback.board.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponseDTO {

    private long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    List<CommentResponseDTO> children;
    private String writer;

}
