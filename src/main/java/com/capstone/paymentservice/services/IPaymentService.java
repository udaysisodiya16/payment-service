package com.capstone.paymentservice.services;

import com.capstone.paymentservice.models.PaymentModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IPaymentService {

    String getPaymentLink(String name, String phoneNumber, String email, Long orderId, Double amount);

    Boolean updatePaymentStatus(Long orderId, Long transactionId, String paymentStatus) throws JsonProcessingException;

    PaymentModel getPaymentDetail(Long transactionId);

    List<PaymentModel> getOrderPaymentDetail(Long orderId);

    Boolean issueRefund(Long transactionId, Double amount) throws JsonProcessingException;
}
