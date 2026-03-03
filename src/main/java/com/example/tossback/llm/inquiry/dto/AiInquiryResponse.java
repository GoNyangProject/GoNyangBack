package com.example.tossback.llm.inquiry.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiInquiryResponse {
    private String title;
    private String status;
    private String createdAt;
    private String answer;
}