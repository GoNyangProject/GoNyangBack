package com.example.tossback.board.boardLike.service;

import com.example.tossback.board.dto.BoardRequestDTO;
import com.example.tossback.board.dto.BoardResponseDTO;

public interface LikeService {
    BoardResponseDTO toggleLike(BoardRequestDTO dto);
}
