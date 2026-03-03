package com.example.tossback.llm.notice.service.impl;

import com.example.tossback.board.entity.Board;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.llm.notice.dto.AiNoticeResponse;
import com.example.tossback.llm.notice.service.AiNoticeService;
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
public class AiNoticeServiceImpl implements AiNoticeService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AiNoticeResponse> getRecentNotice() {

        List<Board> notices = boardRepository.findRecentByKeyword(1L, "이벤트", PageRequest.of(0, 5));

        return notices.stream()
                .map(notice -> AiNoticeResponse.builder()
                        .title(notice.getTitle())
                        .content(HtmlUtil.cleanHtml(notice.getContent()))
                        .createAt(notice.getCreatedAt().toLocalDate().toString())
                        .build())
                .collect(Collectors.toList());
    }

}
