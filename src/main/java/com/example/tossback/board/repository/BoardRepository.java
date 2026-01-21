package com.example.tossback.board.repository;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("SELECT b FROM Board b WHERE b.boardType.boardCode = :boardCode " +
            "AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword%)")
    Page<Board> searchBoards(@Param("boardCode") BoardCode boardCode, @Param("searchKeyword") String keyword, Pageable pageable);

    Board findById(@Param("boardCode") long boardCode);

}
