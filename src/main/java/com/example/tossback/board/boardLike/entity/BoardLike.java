package com.example.tossback.board.boardLike.entity;

import com.example.tossback.board.entity.Board;
import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "board_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_board_like_member_board",
                        columnNames = {"member_id", "board_id"}
                )
        }
)
public class BoardLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}