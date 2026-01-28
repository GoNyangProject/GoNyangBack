package com.example.tossback.admin.block.servcie;

import com.example.tossback.admin.block.dto.AdminBlockRequest;
import com.example.tossback.admin.block.dto.AdminBlockResponse;

import java.util.List;

public interface AdminBlockService {
    Boolean blockDate(AdminBlockRequest request);
    List<AdminBlockResponse> getBlockList();
    Boolean unblockDate(String date);
}
