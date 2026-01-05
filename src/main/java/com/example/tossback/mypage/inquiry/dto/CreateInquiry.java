package com.example.tossback.mypage.inquiry.dto;

import com.example.tossback.mypage.inquiry.enums.InquiryCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInquiry {
    private String userId;
    private String title;
    private String content;
    private InquiryCategory category;
}
