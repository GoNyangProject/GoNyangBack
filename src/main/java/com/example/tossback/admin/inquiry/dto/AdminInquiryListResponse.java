package com.example.tossback.admin.inquiry.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminInquiryListResponse {
    private List<AdminInquiryList> content;
    private int totalPages;
    private long totalElements;

}
