package com.example.tossback.admin.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminCommunityListResponseDTO {
    private List<CommunityInfo> content;
    private int totalPages;
    private long totalElements;
}