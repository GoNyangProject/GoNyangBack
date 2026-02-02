package com.example.tossback.admin.community.service;

import com.example.tossback.admin.community.dto.AdminCommunityListResponseDTO;

public interface AdminCommunityService {
    AdminCommunityListResponseDTO getCommunityList(String search, String status, String category, String sort, int page, int size);
}
