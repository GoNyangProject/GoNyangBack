package com.example.tossback.llm.community.service;

import com.example.tossback.llm.community.dto.AiCommunityResponse;

import java.util.List;

public interface AiCommunityService {
    List<AiCommunityResponse> searchCommunity(String keyword);
}
