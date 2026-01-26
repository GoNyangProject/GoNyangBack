package com.example.tossback.community.service;

import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.community.dto.CommunityListResponse;
import com.example.tossback.community.dto.CommunitySaveRequest;
import com.example.tossback.community.dto.FileUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface CommunityService {
    CommunityListResponse getCommunityList(int page, int size, BoardCode boardCode);
    FileUploadResponseDto uploadFile(MultipartFile file);
    Boolean saveCommunity(CommunitySaveRequest saveRequest);
}
