package com.example.tossback.admin.community.service.impl;

import com.example.tossback.admin.community.dto.AdminCommunityListResponseDTO;
import com.example.tossback.admin.community.dto.CommunityInfo;
import com.example.tossback.admin.community.service.AdminCommunityService;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCommunityServiceImpl implements AdminCommunityService {
    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminCommunityListResponseDTO getCommunityList(String search, String status, String category, String sort, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Board> boardPage = boardRepository.findAdminBoardList(pageable, search, status, category, sort);

        List<CommunityInfo> dtoList = boardPage.getContent().stream()
                .map(board -> CommunityInfo.builder()
                        .id(board.getId())
                        .boardName(String.valueOf(board.getBoardType().getBoardCode()))
                        .title(board.getTitle())
                        .userId(board.getMember().getUserId())
                        .viewCount(board.getViewCount())
                        .likeCount(board.getLikeCount())
                        .createdAt(board.getCreatedAt().toString())
                        .deletedAt(board.getDeletedAt() != null ? board.getDeletedAt().toString() : null)
                        .build())
                .collect(Collectors.toList());

        return AdminCommunityListResponseDTO.builder()
                .content(dtoList)
                .totalPages(boardPage.getTotalPages())
                .totalElements(boardPage.getTotalElements())
                .build();
    }
}
