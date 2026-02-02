package com.example.tossback.admin.petInfo.service.impl;

import com.example.tossback.admin.petInfo.dto.AdminPetInfoResponse;
import com.example.tossback.admin.petInfo.service.AdminPetInfoService;
import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import com.example.tossback.mypage.accountInfo.repository.PetInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminPetInfoServiceImpl implements AdminPetInfoService {

    private final PetInfoRepository petInfoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AdminPetInfoResponse> getPetList(String memberId) {
        List<PetInfo> pets = petInfoRepository.findAllByMemberUserIdAndDeletedAtIsNull(memberId);
        return pets.stream()
                .map(AdminPetInfoResponse::fromEntity)
                .toList();
    }
}
