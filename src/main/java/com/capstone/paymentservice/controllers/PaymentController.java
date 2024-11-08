package com.capstone.paymentservice.controllers;

import com.capstone.paymentservice.dtos.InitializePaymentDto;
import com.capstone.paymentservice.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> initializePayment(@RequestBody InitializePaymentDto initializePaymentDto) {
        String paymentLink = paymentService.getPaymentLink(initializePaymentDto.getName(),
                initializePaymentDto.getPhoneNumber(), initializePaymentDto.getEmail(),
                initializePaymentDto.getOrderId(), initializePaymentDto.getAmount());
        return ResponseEntity.ok(paymentLink);
    }

    @PostMapping("/callback")
    public ResponseEntity<Boolean> handlePaymentCallback(@RequestBody Map<String, String> payload) {
        Long orderId = Long.valueOf(payload.get("orderId"));
        Long transactionId = Long.valueOf(payload.get("transactionId"));
        String paymentStatus = payload.get("status");
        Boolean status = paymentService.updatePaymentStatus(orderId, transactionId, paymentStatus);
        return ResponseEntity.ok(status);
    }
}
