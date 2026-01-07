package com.example.tossback.board.service.impl;

import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.dto.BoardResultDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.board.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public BoardResultDTO getBoards(BoardCode boardCode, String searchKeyword, int size, int page) {
        BoardResultDTO result = new BoardResultDTO();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());

        Page<Board> boardPage = boardRepository.searchBoards(boardCode, searchKeyword, pageable);
        List<BoardResponseDTO> response = boardPage.getContent().stream().map(board -> {
            BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
            boardResponseDTO.setId(board.getId());
            boardResponseDTO.setTitle(board.getTitle());
            boardResponseDTO.setCreatedAt(board.getCreatedAt());
            return boardResponseDTO;
        }).toList();
        result.setBoards(response);
        result.setTotalCount(boardPage.getTotalElements());

        return result;
    }

    @Override
    public BoardResponseDTO getBoardDetail(long boardCode) {
        BoardResponseDTO result = new BoardResponseDTO();
        Board board = boardRepository.findById(boardCode);
        result.setId(board.getId());
        result.setTitle(board.getTitle());
        result.setContent(board.getContent());
        result.setCreatedAt(board.getCreatedAt());
        return result;
    }
}
