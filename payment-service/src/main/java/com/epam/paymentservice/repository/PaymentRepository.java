package com.epam.paymentservice.repository;

import com.epam.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment deletePaymentByOrderId(Long orderId);
}
