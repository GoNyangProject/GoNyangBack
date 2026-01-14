package com.example.tossback.mypage.inquiry.repository;

import com.example.tossback.mypage.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {
    List<Inquiry> findByUserId(String userId);

    Inquiry findByInquiryNumber(String inquiryNumber);

    @Query("""
        SELECT MAX(i.inquiryNumber)
        FROM Inquiry i
        WHERE i.inquiryNumber LIKE :prefix%
    """)
    String findLastInquiryNumberByPrefix(@Param("prefix") String prefix);
}
