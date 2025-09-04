package com.example.tossback.mypage.inquiry.dto;

import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquiryResponseDTO {
    private String title;
    private Long inquiryNumber;
    private LocalDateTime createdAt;
    private InquiryStatus status;
}
