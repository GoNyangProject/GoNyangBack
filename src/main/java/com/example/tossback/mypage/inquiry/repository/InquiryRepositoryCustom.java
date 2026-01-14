package com.example.tossback.mypage.inquiry.repository;

import com.example.tossback.mypage.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepositoryCustom {
    Page<Inquiry> findInquiryListWithFilters(String search, String category, String status, Pageable pageable);
}