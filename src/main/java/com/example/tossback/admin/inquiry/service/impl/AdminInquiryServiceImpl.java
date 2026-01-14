package com.example.tossback.admin.inquiry.service.impl;

import com.example.tossback.admin.inquiry.dto.AdminInquiryList;
import com.example.tossback.admin.inquiry.dto.AdminInquiryListResponse;
import com.example.tossback.admin.inquiry.dto.AdminRegisterAnswer;
import com.example.tossback.admin.inquiry.service.AdminInquiryService;
import com.example.tossback.mypage.inquiry.entity.Inquiry;
import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import com.example.tossback.mypage.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInquiryServiceImpl implements AdminInquiryService {

    private final InquiryRepository inquiryRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminInquiryListResponse getInquiryList(String search, String category, String inquiryStatus, int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, size, sort);

        String statusFilter = (inquiryStatus == null || inquiryStatus.isEmpty()) ? "PENDING" : inquiryStatus;

        Page<Inquiry> inquiryPage = inquiryRepository.findInquiryListWithFilters(
                search, category, statusFilter, pageable);

        List<AdminInquiryList> adminInquiryList = inquiryPage.getContent().stream()
                .map(AdminInquiryList::from)
                .toList();

        return AdminInquiryListResponse.builder()
                .content(adminInquiryList)
                .totalPages(inquiryPage.getTotalPages())
                .totalElements(inquiryPage.getTotalElements())
                .build();
    }

    @Transactional
    @Override
    public Boolean registerAnswer(AdminRegisterAnswer answer) {
        Inquiry inquiry = inquiryRepository.findById(answer.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 문의가 존재하지 않습니다. ID: " + answer.getId()));
        try{
            inquiry.setAnswer(answer.getAnswer());
            inquiry.setInquiryStatus(InquiryStatus.SUCCESS);
            inquiry.setAnsweredAt(LocalDateTime.now());
            log.info("문의 답변 등록 완료 {}", LocalDateTime.now());
            return true;
        }catch(Exception e){
            log.error("문의 답변 등록 실패 - 문의ID: {}, 사유: {}", answer.getId(), e.getMessage());
            return false;
        }

    }
}
