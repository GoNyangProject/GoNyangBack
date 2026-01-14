package com.example.tossback.admin.inquiry.dto;

import com.example.tossback.mypage.inquiry.entity.Inquiry;
import com.example.tossback.mypage.inquiry.enums.InquiryCategory;
import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class AdminInquiryList {
        private Long id;
        private String name;
        private LocalDateTime createdAt;
        private InquiryCategory category;
        private String inquiryNumber;
        private InquiryStatus inquiryStatus;
        private String content;
        private String title;
        private String answer;

        public static AdminInquiryList from(Inquiry inquiry) {
                return AdminInquiryList.builder()
                        .id(inquiry.getId())
                        .name(inquiry.getUserId())
                        .createdAt(inquiry.getCreatedAt())
                        .category(inquiry.getCategory())
                        .inquiryNumber(inquiry.getInquiryNumber())
                        .inquiryStatus(inquiry.getInquiryStatus())
                        .title(inquiry.getTitle())
                        .answer(inquiry.getAnswer())
                        .build();
        }
}
