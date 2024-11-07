package com.capstone.paymentservice.services;

import com.capstone.paymentservice.paymentgateway.IPaymentGateway;
import com.capstone.paymentservice.paymentgateway.PaymentGatewayChooserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Override
    public String getPaymentLink(String name, String phoneNumber, String email, String orderId, Double amount) {
        IPaymentGateway paymentGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();
        return paymentGateway.getPayLink(name, phoneNumber, email, orderId, amount);
    }
}
