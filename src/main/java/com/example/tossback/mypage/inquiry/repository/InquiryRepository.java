package com.example.tossback.mypage.inquiry.repository;

import com.example.tossback.mypage.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUserId(String userId);

    Inquiry findByInquiryNumber(Long inquiryNumber);
}
