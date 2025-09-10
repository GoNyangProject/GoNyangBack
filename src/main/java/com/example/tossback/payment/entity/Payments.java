package com.example.tossback.payment.entity;

import com.example.tossback.payment.enums.PaymentStatus;
import com.example.tossback.payment.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionKey; // 토스 결제 고유 키

    @Enumerated(EnumType.STRING)
    private PaymentType method;

    private LocalDateTime approvedAt;
    //추후 오더테이블 여기다가 mapping 해야함 order_id랑
}
