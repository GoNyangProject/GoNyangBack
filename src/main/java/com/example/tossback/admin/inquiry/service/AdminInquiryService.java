package com.example.tossback.admin.inquiry.service;

import com.example.tossback.admin.inquiry.dto.AdminInquiryListResponse;
import com.example.tossback.admin.inquiry.dto.AdminRegisterAnswer;

public interface AdminInquiryService {
    AdminInquiryListResponse getInquiryList(String search, String category, String inquiryStatus, int page, int size);
    Boolean registerAnswer(AdminRegisterAnswer answer);
}
