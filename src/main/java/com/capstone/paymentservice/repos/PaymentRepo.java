package com.capstone.paymentservice.repos;

import com.capstone.paymentservice.models.PaymentModel;
import com.capstone.paymentservice.models.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentModel, Long> {

    Optional<PaymentModel> findByOrderIdAndTransactionId(Long orderId, Long transactionId);

    List<PaymentModel> findAllByOrderId(Long orderId);

    Optional<PaymentModel> findAllByOrderIdAndStatus(Long orderId, PaymentStatus paymentStatus);
}
