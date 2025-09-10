package com.example.tossback.payment.dto;

import com.example.tossback.payment.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {

    private PaymentType method;
    private Long amount;
    private String orderId;
    private String orderName;
    private String customerName;
    private String paymentKey;

}
