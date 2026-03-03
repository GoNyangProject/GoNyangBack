package com.example.tossback.llm.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiCommunityResponse {
    private String boardName;
    private String title;
    private String content;
    private String writer;
    private String createdAt;
}
