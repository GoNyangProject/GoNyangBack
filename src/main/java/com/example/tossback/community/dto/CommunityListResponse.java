package com.example.tossback.community.dto;

import com.example.tossback.board.dto.BoardResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommunityListResponse {
    private List<BoardResponseDTO> boards;
    private int totalPages;
    private long totalElements;
}
