package com.example.tossback.board.service;

import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.dto.BoardResultDTO;
import com.example.tossback.board.enums.BoardCode;

public interface BoardService {

    BoardResultDTO getBoards(BoardCode boardCode, String searchKeyword, int size, int page);

    BoardResponseDTO getBoardDetail(long boardCode);

    BoardResponseDTO updateLike(long boardId);

}
