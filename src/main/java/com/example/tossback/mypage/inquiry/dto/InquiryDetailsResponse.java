package com.example.tossback.mypage.inquiry.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InquiryDetailsResponse {
    private String title;
    private String answerUserId;
    private String answer;
    private LocalDateTime answeredAt;
    private String content;
}
