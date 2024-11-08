package com.capstone.paymentservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {

    @Value("${kafka.topic.payment.notification}")
    private String paymentTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentNotification(String message) {
        kafkaTemplate.send(paymentTopic, message);
    }
}
