package com.example.tossback.board.service.impl;

import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.board.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<BoardResponseDTO> getBoards(BoardCode boardCode, String searchKeyword) {
        List<BoardResponseDTO> result = new ArrayList<>();
        List<Board> boards = boardRepository.searchBoards(boardCode, searchKeyword);

        for (Board board : boards) {
            BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
            boardResponseDTO.setId(board.getId());
            boardResponseDTO.setTitle(board.getTitle());
            boardResponseDTO.setContent(board.getContent());
            boardResponseDTO.setCreatedAt(board.getCreatedAt());
            result.add(boardResponseDTO);
        }

        return result;
    }
}
