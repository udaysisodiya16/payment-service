package com.capstone.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializePaymentDto {
    String name;
    String phoneNumber;
    String email;
    String orderId;
}
