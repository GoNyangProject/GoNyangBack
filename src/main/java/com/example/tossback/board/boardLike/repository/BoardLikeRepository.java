package com.example.tossback.board.boardLike.repository;

import com.example.tossback.board.boardLike.entity.BoardLike;
import com.example.tossback.board.entity.Board;
import com.example.tossback.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndMember(Board board, Member member);
    boolean existsByBoardAndMember(Board board, Member member);
}
