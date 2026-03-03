package com.example.tossback.llm.notice.service;

import com.example.tossback.llm.notice.dto.AiNoticeResponse;

import java.util.List;

public interface AiNoticeService {
    List<AiNoticeResponse> getRecentNotice();
}
