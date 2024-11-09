package com.capstone.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationDto {

    private Long orderId;

    private String paymentStatus;

}
