package com.example.tossback.payment.dto;

import com.example.tossback.payment.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponseDTO {
    private String status;
    private LocalDateTime approvedAt;
    private String orderName;
    private Long amount;
    private PaymentType method;
}
