package com.example.tossback.admin.petInfo.service;

import com.example.tossback.admin.petInfo.dto.AdminPetInfoResponse;

import java.util.List;

public interface AdminPetInfoService {
    List<AdminPetInfoResponse> getPetList(String memberId);
}
