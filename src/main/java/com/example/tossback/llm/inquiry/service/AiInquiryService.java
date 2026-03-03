package com.example.tossback.llm.inquiry.service;

import com.example.tossback.llm.inquiry.dto.AiInquiryResponse;

import java.util.List;

public interface AiInquiryService {
    List<AiInquiryResponse> getInquiryStatus();
}
