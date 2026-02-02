package com.example.tossback.board.repository;

import com.example.tossback.board.entity.Board;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Board findById(@Param("boardCode") long boardCode);

    Board findByBoardType_BoardCode(BoardCode boardCode);
    Optional<Board> findBoardById(Long id);
}
