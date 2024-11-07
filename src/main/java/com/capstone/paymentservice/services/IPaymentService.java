package com.capstone.paymentservice.services;

public interface IPaymentService {

    String getPaymentLink(String name, String phoneNumber, String email, String orderId, Double amount);

}
