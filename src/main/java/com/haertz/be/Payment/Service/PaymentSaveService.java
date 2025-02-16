package com.haertz.be.payment.service;

import com.haertz.be.payment.repository.PaymentRepository;
import com.haertz.be.payment.dto.PaymentSaveDto;
import com.haertz.be.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log
@Transactional
public class PaymentSaveService {
    private final PaymentRepository paymentRepository; //// 결제 내역을 저장할 레포지토리

    public Payment savePayment(PaymentSaveDto paymentSaveDto) {
        Payment payment = new Payment();
        payment.setUserId(paymentSaveDto.getUserId()); // 회원 ID
        //payment.setReservationId(paymentSaveDto.getReservationId()); //결제 ID
        payment.setTotalAmount(paymentSaveDto.getTotalAmount());  // 결제 금액
        payment.setPartnerOrderId(paymentSaveDto.getPartnerOrderId());  // 외부 결제 서비스에서 사용하는 주문 ID
        payment.setPaymentTransaction(paymentSaveDto.getPaymentTransaction());  // 결제 트랜잭션 ID
        payment.setPaymentMethod(paymentSaveDto.getPaymentMethod());  // 결제 방법
        payment.setPaymentDate(paymentSaveDto.getPaymentDate());
        payment.setPaymentStatus(paymentSaveDto.getPaymentStatus());
        return paymentRepository.save(payment);
    }
}
