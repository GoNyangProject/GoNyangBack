package com.example.tossback.board.repository;

import com.example.tossback.board.entity.Board;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Board findById(@Param("boardCode") long boardCode);

}
