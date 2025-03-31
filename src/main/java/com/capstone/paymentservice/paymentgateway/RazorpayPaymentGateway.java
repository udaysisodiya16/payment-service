package com.capstone.paymentservice.paymentgateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RazorpayPaymentGateway implements IPaymentGateway {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("$(callback.url)")
    private String callbackUrl;

    @Override
    public String getPayLink(String name, String phoneNumber, String email, Long orderId, Double amount, Long transactionId) {
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
            paymentLinkRequest.put("expire_by", 1726766856);
            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "Payment for policy no #23456");

            JSONObject customer = new JSONObject();
            customer.put("name", phoneNumber);
            customer.put("contact", name);
            customer.put("email", email);
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);

            JSONObject notes = new JSONObject();
            notes.put("order_id", orderId.toString());
            notes.put("transaction_id", transactionId.toString());
            paymentLinkRequest.put("notes", notes);

            paymentLinkRequest.put("callback_url", callbackUrl);
            paymentLinkRequest.put("callback_method", "post");
            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url").toString();
        } catch (RazorpayException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Boolean issueRefund(Long transactionId, Double amount) {
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("amount", amount * 100);
        refundRequest.put("transaction_id", transactionId.toString());
        try {
            Refund refund = razorpayClient.refunds.create(refundRequest);
            return refund.get("id") != null;
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
}
