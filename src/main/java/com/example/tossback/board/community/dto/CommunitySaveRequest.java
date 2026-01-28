package com.example.tossback.board.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunitySaveRequest {
    private String title;
    private String content;
    private String boardCode;
}
