package com.example.tossback.payment.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.payment.dto.PaymentRequestDto;
import com.example.tossback.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> pay(@RequestBody PaymentRequestDto request) {
        return new ResponseEntity<>(new CommonResponse(paymentService.pay(request)), HttpStatus.OK);
    }
}
