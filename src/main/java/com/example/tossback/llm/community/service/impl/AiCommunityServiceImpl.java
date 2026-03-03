package com.example.tossback.llm.community.service.impl;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.llm.community.dto.AiCommunityResponse;
import com.example.tossback.llm.community.service.AiCommunityService;
import com.example.tossback.util.HtmlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiCommunityServiceImpl implements AiCommunityService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AiCommunityResponse> searchCommunity(String keyword) {

        List<BoardCode> communityCodes = List.of(
                BoardCode.FREE_COMMUNITY,
                BoardCode.FLEA_MARKET,
                BoardCode.INFO
        );

        log.info("조회 키워드: " + keyword);
        log.info("조회 대상 코드: " + communityCodes);

        List<Board> results = boardRepository.searchCommunity(communityCodes, keyword, PageRequest.of(0, 5));

        log.info("조회된 글 개수: " + results.size());
        return results.stream()
                .map(post -> AiCommunityResponse.builder()
                        .boardName(post.getBoardType().getBoardCode().name())
                        .title(post.getTitle())
                        .content(HtmlUtil.cleanHtml(post.getContent()))
                        .writer(post.getMember().getUsername())
                        .createdAt(post.getCreatedAt().toLocalDate().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
