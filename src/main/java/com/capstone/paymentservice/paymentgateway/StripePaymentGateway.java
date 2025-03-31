package com.capstone.paymentservice.paymentgateway;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Refund;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements IPaymentGateway {

    @Value("${stripe.key}")
    private String apiKey;

    @Value("$(callback.url)")
    private String callbackUrl;

    @Override
    public String getPayLink(String name, String phoneNumber, String email, Long orderId, Double amount, Long transactionId) {
        try {
            Stripe.apiKey = this.apiKey;

            Price price = getPrice(amount);

            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            ).setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl(callbackUrl).build()).build())
                            .build();
            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        } catch (StripeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Price getPrice(Double amount) {
        try {
            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("INR")
                            .setUnitAmount(amount.longValue())
                            .setRecurring(
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()

                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                            )
                            .build();
            return Price.create(params);
        } catch (StripeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public Boolean issueRefund(Long transactionId, Double amount) {
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(transactionId.toString())
                .setAmount((long) (amount * 100))
                .build();
        try {
            Refund refund = Refund.create(params);
            return refund.getId() != null;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
