package com.capstone.paymentservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IPaymentService {

    String getPaymentLink(String name, String phoneNumber, String email, Long orderId, Double amount);

    Boolean updatePaymentStatus(Long orderId, Long transactionId, String paymentStatus) throws JsonProcessingException;

}
