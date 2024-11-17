package com.capstone.paymentservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitializePaymentDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String email;

    @NotNull
    @Positive
    private Long orderId;

    @NotNull
    @Positive
    private Double amount;

}
