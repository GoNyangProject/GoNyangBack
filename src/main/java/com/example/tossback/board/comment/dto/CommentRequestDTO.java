package com.example.tossback.board.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    private long boardId;
    private long memberId;
    private long parentId;
    private String content;

}
