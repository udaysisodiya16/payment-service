package com.capstone.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializePaymentDto {

    private String name;
    private String phoneNumber;
    private String email;
    private String orderId;
    private Double amount;

}
