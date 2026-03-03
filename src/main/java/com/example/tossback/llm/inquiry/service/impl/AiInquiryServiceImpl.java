package com.example.tossback.llm.inquiry.service.impl;

import com.example.tossback.llm.inquiry.dto.AiInquiryResponse;
import com.example.tossback.llm.inquiry.service.AiInquiryService;
import com.example.tossback.mypage.inquiry.entity.Inquiry;
import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import com.example.tossback.mypage.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiInquiryServiceImpl implements AiInquiryService {

    private final InquiryRepository inquiryRepository;
    @Override
    public List<AiInquiryResponse> getInquiryStatus() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Inquiry> myInquiries = inquiryRepository.findMyInquiries(userId, PageRequest.of(0, 3));

        return myInquiries.stream()
                .map(q -> AiInquiryResponse.builder()
                        .title(q.getTitle())
                        .status(q.getInquiryStatus() == InquiryStatus.SUCCESS ? "답변완료" : "답변대기")
                        .createdAt(q.getCreatedAt().toLocalDate().toString())
                        .answer(q.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }
}
