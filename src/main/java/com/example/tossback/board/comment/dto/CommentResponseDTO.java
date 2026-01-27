package com.example.tossback.board.comment.dto;

import com.example.tossback.board.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentResponseDTO {

    private long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    List<CommentResponseDTO> children;
    private String writer;

    public static CommentResponseDTO from(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setWriter(comment.getMember().getUsername());
        dto.setCreatedAt(comment.getCreatedAt());

        dto.setChildren(comment.getChildren().stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList()));

        return dto;
    }

}
