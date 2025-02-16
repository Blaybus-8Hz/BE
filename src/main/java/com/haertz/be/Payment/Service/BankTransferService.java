package com.haertz.be.payment.service;

import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.payment.dto.BankTransferDto;
import com.haertz.be.payment.dto.BankTransferRequestDto;
import com.haertz.be.payment.exception.PaymentErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log
public class BankTransferService {
    private final PaymentSaveService paymentSaveService;

    public static BankTransferDto banktransferrequest(BankTransferRequestDto requestDTO) {
        // 계좌이체 요청 정보가 유효한지 확인
        if (requestDTO == null || requestDTO.getTid() == null || requestDTO.getPartner_order_id() == null) {
            throw new BaseException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
        }
        try {
            /*
            // 결제 내역 저장 로직
            PaymentSaveDto paymentSaveDto = new PaymentSaveDto();
            paymentSaveDto.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
            paymentSaveDto.setPaymentDate(new Date());
            paymentSaveDto.setPaymentStatus(PaymentStatus.PENDING);
            paymentSaveService.savePayment(paymentSaveDto);

             */
            // 계좌이체 후 DTO 반환
            BankTransferDto bankTransferDto = new BankTransferDto();
            bankTransferDto.setGoogleMeetingLink("구글미팅 링크 생성로직은 아직 구현 전..");
            bankTransferDto.setCreated_at(new Date());
            return bankTransferDto;
        } catch (Exception ex) {
            // 결제 처리 중 오류 발생 시
            throw new BaseException(PaymentErrorCode.PAYMENT_PROCESSING_ERROR);
        }
    }
}
