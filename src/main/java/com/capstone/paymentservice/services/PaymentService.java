package com.capstone.paymentservice.services;

import com.capstone.paymentservice.clients.KafkaClient;
import com.capstone.paymentservice.dtos.PaymentNotificationDto;
import com.capstone.paymentservice.models.PaymentModel;
import com.capstone.paymentservice.models.PaymentStatus;
import com.capstone.paymentservice.paymentgateway.IPaymentGateway;
import com.capstone.paymentservice.paymentgateway.PaymentGatewayChooserStrategy;
import com.capstone.paymentservice.repos.PaymentRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private KafkaClient kafkaClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String getPaymentLink(String name, String phoneNumber, String email, Long orderId, Double amount) {
        PaymentModel payment = new PaymentModel();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        payment = paymentRepo.save(payment);

        IPaymentGateway paymentGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();
        return paymentGateway.getPayLink(name, phoneNumber, email, orderId, amount, payment.getTransactionId());
    }

    @Override
    public Boolean updatePaymentStatus(Long orderId, Long transactionId, String paymentStatus) throws JsonProcessingException {
        //Call validate Token Api
        PaymentModel payment = paymentRepo.findByOrderIdAndTransactionId(orderId, transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid orderId Or transactionId"));
        payment.setStatus(PaymentStatus.valueOf(paymentStatus));
        paymentRepo.save(payment);

        if (PaymentStatus.SUCCESS.name().equals(paymentStatus)) {
            PaymentNotificationDto paymentNotificationDto = new PaymentNotificationDto();
            paymentNotificationDto.setOrderId(orderId);
            paymentNotificationDto.setPaymentStatus(paymentStatus);
            String message = objectMapper.writeValueAsString(paymentNotificationDto);
            kafkaClient.sendPaymentNotification(message);
        }

        return true;
    }
}
