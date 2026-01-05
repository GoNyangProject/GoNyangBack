package com.example.tossback.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDTO {

    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

}
