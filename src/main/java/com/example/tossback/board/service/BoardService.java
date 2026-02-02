package com.example.tossback.board.service;

import com.example.tossback.board.dto.BoardRequestDTO;
import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.dto.BoardResultDTO;
import com.example.tossback.board.enums.BoardCode;

public interface BoardService {

    BoardResultDTO getBoards(BoardCode boardCode, String searchKeyword, int size, int page, String sort);

    BoardResponseDTO getBoardDetail(long boardCode, String userId);

    BoardResponseDTO updateLike(long boardId);

    Boolean deleteBoard(BoardRequestDTO boardRequestDTO);

}
