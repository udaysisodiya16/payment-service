package com.capstone.paymentservice.paymentgateway;

public interface IPaymentGateway {

    String getPayLink(String name, String phoneNumber, String email, Long orderId, Double amount, Long transactionId);

}
