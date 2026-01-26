package com.example.tossback.board.repository;

import com.example.tossback.board.entity.BoardType;
import com.example.tossback.board.enums.BoardCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {
    Optional<BoardType> findByBoardCode(BoardCode boardCode);
}
