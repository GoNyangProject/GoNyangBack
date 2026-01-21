package com.example.tossback.board.comment.dto;

import com.example.tossback.board.enums.BoardCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    private BoardCode boardCode;
    private long memberId;
    private long parentId;
    private String content;

}
