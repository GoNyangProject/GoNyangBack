package com.example.tossback.payment.service.impl;

import com.example.tossback.payment.dto.PaymentRequestDto;
import com.example.tossback.payment.dto.PaymentResponseDTO;
import com.example.tossback.payment.entity.Payments;
import com.example.tossback.payment.enums.PaymentStatus;
import com.example.tossback.payment.repository.PaymentsRepository;
import com.example.tossback.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentsRepository paymentsRepository;

    public PaymentServiceImpl(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public PaymentResponseDTO pay(PaymentRequestDto request) {
        PaymentResponseDTO response = new PaymentResponseDTO();
        LocalDateTime now = LocalDateTime.now();
        try {
            Payments payments = new Payments();
            payments.setAmount(request.getAmount());
            payments.setStatus(PaymentStatus.SUCCESS);
            payments.setTransactionKey(request.getPaymentKey());
            payments.setMethod(request.getMethod());
            payments.setApprovedAt(now);
            paymentsRepository.save(payments);

        } catch (Exception e) {
            log.error("결제 저장 중 오류 발생. transactionKey: {}", request.getPaymentKey(), e);
            response.setStatus("fail");
            return response;
        }
        response.setStatus("success");
        response.setApprovedAt(now);
        response.setOrderName(request.getOrderName());
        response.setAmount(request.getAmount());
        response.setMethod(request.getMethod());
        return response;
    }

}
