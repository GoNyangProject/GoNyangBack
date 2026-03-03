package com.example.tossback.board.repository;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    @EntityGraph(attributePaths = {"member"})
    Board findById(@Param("boardCode") long boardCode);

    Optional<Board> findBoardById(Long id);

    @Query("SELECT b FROM Board b WHERE b.boardType.boardTypeId = :typeId " +
            "AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) " +
            "ORDER BY b.createdAt DESC")
    List<Board> findRecentByKeyword(@Param("typeId") Long typeId,
                                    @Param("keyword") String keyword,
                                    Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.boardType.boardCode IN :codes " +
            "AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) " +
            "ORDER BY b.createdAt DESC")
    List<Board> searchCommunity(@Param("codes") List<BoardCode> codes,
                                @Param("keyword") String keyword,
                                Pageable pageable);
}
