package com.example.tossback.mypage.inquiry.entity;

import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.mypage.inquiry.enums.InquiryCategory;
import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Inquiry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String title;
    private String content;
    private String inquiryNumber;
    @Enumerated(EnumType.STRING)
    private InquiryStatus inquiryStatus;
    private String answer;
    private LocalDateTime answeredAt;
    private String answerUserId;
    @Enumerated(EnumType.STRING)
    private InquiryCategory category;
}
