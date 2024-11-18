package com.capstone.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDetailResponse {

    private Long transactionId;

    private Long orderId;

    private double amount;

    private String status; // SUCCESS, FAILED, PENDING

    private LocalDateTime createdAt;

    private String receiptUrl;

}
