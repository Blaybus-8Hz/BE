package com.haertz.be.payment.repository;

import com.haertz.be.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface temp extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPartnerOrderId(String partnerOrderId);
    //Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentId(Long paymentId);
    Optional<Payment> findByPaymentTransaction(String paymentTransaction);
}

