package com.capstone.paymentservice.repos;

import com.capstone.paymentservice.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentModel, Long> {

    Optional<PaymentModel> findByOrderIdAndTransactionId(Long orderId, Long transactionId);
}
