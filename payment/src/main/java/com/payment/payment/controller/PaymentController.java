package com.payment.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment.dto.PaymentRequest;
import com.payment.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity makePayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok().body(paymentService.makePayment(paymentRequest));
    }

}
