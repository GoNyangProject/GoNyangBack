package com.example.tossback.mypage.accountInfo.repository;

import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetInfoRepository extends JpaRepository<PetInfo, Long> {
}

