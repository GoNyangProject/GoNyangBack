package com.example.tossback.payment.service;

import com.example.tossback.payment.dto.PaymentRequestDto;
import com.example.tossback.payment.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO pay(PaymentRequestDto request);
}
