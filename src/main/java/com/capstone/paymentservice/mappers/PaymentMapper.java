package com.capstone.paymentservice.mappers;

import com.capstone.paymentservice.dtos.PaymentDetailResponse;
import com.capstone.paymentservice.models.PaymentModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    List<PaymentDetailResponse> paymentModelsToPaymentDetailResponses(List<PaymentModel> paymentDetails);

    PaymentDetailResponse paymentModelToPaymentDetailResponse(PaymentModel paymentDetail);
}
