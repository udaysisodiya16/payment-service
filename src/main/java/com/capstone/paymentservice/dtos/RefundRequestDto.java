package com.capstone.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefundRequestDto {
    private Long transactionId;
    private Double amount;
}
