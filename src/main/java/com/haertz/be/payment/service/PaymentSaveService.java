package com.haertz.be.payment.service;

import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.payment.exception.PaymentErrorCode;
import com.haertz.be.payment.dto.PaymentSaveDto;
import com.haertz.be.payment.entity.Payment;
import com.haertz.be.payment.repository.temp;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log
@Transactional
public class PaymentSaveService {
    private final temp temp; //// 결제 내역을 저장할 레포지토리

    public Payment savePayment(PaymentSaveDto paymentSaveDto) {
        // paymentSaveDto 필드들이 null이면 예외를 발생시킴
        if (paymentSaveDto == null || paymentSaveDto.getUserId() == null || paymentSaveDto.getTotalAmount() == null || paymentSaveDto.getPaymentMethod() == null || paymentSaveDto.getPaymentDate() == null || paymentSaveDto.getPaymentStatus() == null) {
            throw new BaseException(PaymentErrorCode.INVALID_PAYMENT_REQUEST); // 유효하지 않은 결제 요청
        }
        Payment payment = new Payment();
        payment.setUserId(paymentSaveDto.getUserId());  // 회원 ID
        payment.setTotalAmount(paymentSaveDto.getTotalAmount());  // 결제 금액
        payment.setPaymentMethod(paymentSaveDto.getPaymentMethod());  // 결제 방법
        payment.setPaymentDate(paymentSaveDto.getPaymentDate()); // 결제 날짜
        payment.setPaymentStatus(paymentSaveDto.getPaymentStatus()); // 결제 상태
        //계좌이체에서는 null값
        payment.setPartnerOrderId(paymentSaveDto.getPartnerOrderId());  // 외부 결제 서비스에서 사용하는 주문 ID
        payment.setPaymentTransaction(paymentSaveDto.getPaymentTransaction());
        try {
            return temp.save(payment); // 결제 저장
        } catch (Exception e) {
            throw new BaseException(PaymentErrorCode.PAYMENT_PROCESSING_ERROR); // 예외 처리 후 다시 던지기
        }
    }
}
