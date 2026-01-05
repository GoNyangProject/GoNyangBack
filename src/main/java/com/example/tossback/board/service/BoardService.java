package com.example.tossback.board.service;

import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.enums.BoardCode;

import java.util.List;

public interface BoardService {

    List<BoardResponseDTO> getBoards(BoardCode boardCode, String searchKeyword);

}
