package com.example.tossback.mypage.inquiry.service.impl;

import com.example.tossback.mypage.inquiry.dto.InquiryDetailsRequest;
import com.example.tossback.mypage.inquiry.dto.InquiryDetailsResponse;
import com.example.tossback.mypage.inquiry.dto.InquiryResponseDTO;
import com.example.tossback.mypage.inquiry.entity.Inquiry;
import com.example.tossback.mypage.inquiry.repository.InquiryRepository;
import com.example.tossback.mypage.inquiry.service.InquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public List<InquiryResponseDTO> getInquiry(String userId) {
        try {
            List<Inquiry> inquiryList = inquiryRepository.findByUserId(userId);

            return inquiryList.stream().map(i -> {
                InquiryResponseDTO inquiryResponseDTO = new InquiryResponseDTO();
                inquiryResponseDTO.setInquiryNumber(i.getInquiryNumber());
                inquiryResponseDTO.setStatus(i.getInquiryStatus());
                inquiryResponseDTO.setTitle(i.getTitle());
                inquiryResponseDTO.setCreatedAt(i.getCreatedAt());
                return inquiryResponseDTO;
            }).toList();

        } catch (Exception e) {
            log.error("Inquiry 조회 중 예외 발생. userId={}", userId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public InquiryDetailsResponse getInquiryDetails(InquiryDetailsRequest inquiryDetailsRequest) {
        try {
            Inquiry inquiryDetail = inquiryRepository.findByInquiryNumber(inquiryDetailsRequest.getInquiryNumber());

            if (inquiryDetail == null) {
                log.warn("문의 정보를 찾을 수 없습니다. 문의 번호: {}", inquiryDetailsRequest.getInquiryNumber());
                throw new RuntimeException("해당 문의를 찾을 수 없습니다.");
            }

            InquiryDetailsResponse dto = new InquiryDetailsResponse();
            dto.setTitle(inquiryDetail.getTitle());
            dto.setContent(inquiryDetail.getContent());
            dto.setAnsweredAt(inquiryDetail.getAnsweredAt());
            dto.setAnswer(inquiryDetail.getAnswer());
            dto.setAnswerUserId(inquiryDetail.getAnswerUserId());
            return dto;

        } catch (Exception e) {
            log.error("문의 상세 정보를 불러오는 중 오류 발생. 문의 번호: {}. 오류: {}",
                    inquiryDetailsRequest.getInquiryNumber(), e.getMessage(), e);
            throw new RuntimeException("문의 상세 정보를 불러오는 중 오류가 발생했습니다.");
        }
    }
}
