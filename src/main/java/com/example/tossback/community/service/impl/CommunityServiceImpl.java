package com.example.tossback.community.service.impl;

import com.example.tossback.admin.inquiry.dto.AdminInquiryList;
import com.example.tossback.admin.inquiry.dto.AdminInquiryListResponse;
import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.community.dto.CommunityListResponse;
import com.example.tossback.community.service.CommunityService;
import com.example.tossback.mypage.inquiry.entity.Inquiry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final BoardRepository boardRepository;

    @Override
    public CommunityListResponse getCommunityList(int page, int size, BoardCode boardCode) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardPage = boardRepository.findCommunityListWithFilters(pageable, boardCode);

        CommunityListResponse response = new CommunityListResponse();

        List<BoardResponseDTO> boardDtos = boardPage.getContent().stream()
                .map(BoardResponseDTO::fromEntity)
                .collect(Collectors.toList());

        response.setBoards(boardDtos);
        response.setTotalPages(boardPage.getTotalPages());
        response.setTotalElements(boardPage.getTotalElements());

        return response;
    }
}
