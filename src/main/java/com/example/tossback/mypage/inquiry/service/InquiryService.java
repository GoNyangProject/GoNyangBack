package com.example.tossback.mypage.inquiry.service;

import com.example.tossback.mypage.inquiry.dto.InquiryDetailsRequest;
import com.example.tossback.mypage.inquiry.dto.InquiryDetailsResponse;
import com.example.tossback.mypage.inquiry.dto.InquiryResponseDTO;

import java.util.List;

public interface InquiryService {
    List<InquiryResponseDTO> getInquiry(String userId);
    InquiryDetailsResponse getInquiryDetails(InquiryDetailsRequest inquiryDetailsRequest);
}
