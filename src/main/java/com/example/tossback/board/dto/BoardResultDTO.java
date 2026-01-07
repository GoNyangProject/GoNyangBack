package com.example.tossback.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResultDTO {
    private List<BoardResponseDTO> boards;
    private long totalCount;
}
