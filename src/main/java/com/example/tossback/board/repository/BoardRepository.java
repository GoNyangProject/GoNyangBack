package com.example.tossback.board.repository;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE b.boardType.boardCode = :boardCode " +
            "AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword%)")
    List<Board> searchBoards(@Param("boardCode") BoardCode boardCode, @Param("searchKeyword") String keyword);
}
