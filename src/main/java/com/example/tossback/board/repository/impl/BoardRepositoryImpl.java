package com.example.tossback.board.repository.impl;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepositoryCustom;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.tossback.board.entity.QBoard.board;
import static com.example.tossback.member.entity.QMember.member;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<Board> findCommunityListWithFilters(Pageable pageable, BoardCode boardCode, String search, String sort) {
        List<Board> content = queryFactory
                .selectFrom(board)
                .leftJoin(board.member, member).fetchJoin()
                .where(
                        boardCodeEq(boardCode),
                        searchKeywordLike(search)
                )
                .orderBy(getSortOrder(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(board.count())
                .from(board)
                .where(boardCodeEq(boardCode),searchKeywordLike(search))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
    private BooleanExpression searchKeywordLike(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        return board.title.containsIgnoreCase(keyword)
                .or(board.content.containsIgnoreCase(keyword))
                .or(member.userId.containsIgnoreCase(keyword));
    }
    private OrderSpecifier<?> getSortOrder(String sort) {
        if (sort == null) return board.createdAt.desc();

        return switch (sort) {
            case "views" ->
                    board.viewCount.desc();
            case "likes" -> board.likeCount.desc();
            default -> board.createdAt.desc();
        };
    }

    private BooleanExpression boardCodeEq(BoardCode boardCode) {
        if (boardCode == null) return null;
        if (boardCode == BoardCode.FREE_COMMUNITY) {
            return board.boardType.boardTypeId.eq(3L);
        } else if (boardCode == BoardCode.NOTICE) {
            return board.boardType.boardTypeId.eq(1L);
        } else if (boardCode == BoardCode.INQUIRY) {
            return board.boardType.boardTypeId.eq(2L);
        } else if (boardCode == BoardCode.INFO) {
            return board.boardType.boardTypeId.eq(5L);
        } else if (boardCode == BoardCode.FLEA_MARKET) {
            return board.boardType.boardTypeId.eq(4L);
        }
        return null;
    }
}
