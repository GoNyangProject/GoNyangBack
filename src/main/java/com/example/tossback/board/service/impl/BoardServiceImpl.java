package com.example.tossback.board.service.impl;

import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.dto.BoardResultDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.board.service.BoardService;
import com.example.tossback.config.redis.util.RedisUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final RedisUtil redisUtil;

    public BoardServiceImpl(BoardRepository boardRepository, RedisUtil redisUtil) {
        this.boardRepository = boardRepository;
        this.redisUtil = redisUtil;
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
            boardResponseDTO.setViewCount(board.getViewCount());
            boardResponseDTO.setLikeCount(board.getLikeCount());
            boardResponseDTO.setImgUrl(board.getImgUrl());
            return boardResponseDTO;
        }).toList();
        result.setBoards(response);
        result.setTotalCount(boardPage.getTotalElements());

        return result;
    }

    @Override
    @Transactional
    public BoardResponseDTO getBoardDetail(long boardCode, String userId) {
        BoardResponseDTO result = new BoardResponseDTO();
        Board board = boardRepository.findById(boardCode);
        String redisKey = "view:board:" + boardCode + ":user:" + userId;
        String isViewed = redisUtil.getData(redisKey);
        if (isViewed == null) {
            board.incrementViewCount();
            redisUtil.setDataExpire(redisKey, "visited", 86400);
        }
        result.setId(board.getId());
        result.setTitle(board.getTitle());
        result.setContent(board.getContent());
        result.setCreatedAt(board.getCreatedAt());
        result.setLikeCount(board.getLikeCount());
        result.setViewCount(board.getViewCount());
        result.setImgUrl(board.getImgUrl());
        return result;
    }

    @Override
    public BoardResponseDTO updateLike(long boardId) {
        BoardResponseDTO result = new BoardResponseDTO();
        Board board = boardRepository.findById(boardId);
        board.setLikeCount(board.getLikeCount() + 1);
        boardRepository.save(board);

        result.setId(board.getId());
        result.setTitle(board.getTitle());
        result.setContent(board.getContent());
        result.setCreatedAt(board.getCreatedAt());
        result.setLikeCount(board.getLikeCount());
        result.setViewCount(board.getViewCount());
        return result;
    }
}
