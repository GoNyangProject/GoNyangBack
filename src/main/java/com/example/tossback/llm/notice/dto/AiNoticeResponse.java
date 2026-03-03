package com.example.tossback.llm.notice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiNoticeResponse {
    private String title;
    private String content;
    private String createAt;
}
