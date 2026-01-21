package com.example.tossback.board.repository;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.mypage.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> findCommunityListWithFilters(Pageable pageable, BoardCode boardCode);
}
