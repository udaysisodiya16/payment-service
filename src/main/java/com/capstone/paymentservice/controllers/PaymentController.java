package com.capstone.paymentservice.controllers;

import com.capstone.paymentservice.dtos.InitializePaymentDto;
import com.capstone.paymentservice.dtos.PaymentDetailResponse;
import com.capstone.paymentservice.dtos.RefundRequestDto;
import com.capstone.paymentservice.mappers.PaymentMapper;
import com.capstone.paymentservice.models.PaymentModel;
import com.capstone.paymentservice.services.IPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @PostMapping
    public ResponseEntity<String> initializePayment(@RequestBody @Valid InitializePaymentDto initializePaymentDto) {
        String paymentLink = paymentService.getPaymentLink(initializePaymentDto.getName(),
                initializePaymentDto.getPhoneNumber(), initializePaymentDto.getEmail(),
                initializePaymentDto.getOrderId(), initializePaymentDto.getAmount());
        return ResponseEntity.ok(paymentLink);
    }

    @PostMapping("$(callback.url)")
    public ResponseEntity<Boolean> handlePaymentCallback(@RequestBody Map<String, String> payload) throws JsonProcessingException {
        @NotNull @Positive Long orderId = Long.valueOf(payload.get("order_id"));
        @NotNull @Positive Long transactionId = Long.valueOf(payload.get("transaction_id"));
        @NotBlank String paymentStatus = payload.get("status");
        Boolean status = paymentService.updatePaymentStatus(orderId, transactionId, paymentStatus);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentDetailResponse> getPaymentDetail(@PathVariable Long transactionId) throws JsonProcessingException {
        PaymentModel paymentDetail = paymentService.getPaymentDetail(transactionId);
        return ResponseEntity.ok(paymentMapper.paymentModelToPaymentDetailResponse(paymentDetail));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDetailResponse>> getOrderPaymentDetail(@PathVariable Long orderId) throws JsonProcessingException {
        List<PaymentModel> paymentDetails = paymentService.getOrderPaymentDetail(orderId);
        return ResponseEntity.ok(paymentMapper.paymentModelsToPaymentDetailResponses(paymentDetails));
    }

    @PostMapping("/issueRefund")
    public ResponseEntity<Boolean> issueRefund(@RequestBody RefundRequestDto refundRequestDto) throws JsonProcessingException {
        return ResponseEntity.ok(paymentService.issueRefund(refundRequestDto.getTransactionId(),
                refundRequestDto.getAmount()));
    }
}
