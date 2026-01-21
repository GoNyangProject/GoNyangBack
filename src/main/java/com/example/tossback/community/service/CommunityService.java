package com.example.tossback.community.service;

import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.community.dto.CommunityListResponse;

public interface CommunityService {
    CommunityListResponse getCommunityList(int page, int size, BoardCode boardCode);
}
