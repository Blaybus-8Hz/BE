package com.haertz.be.payment.service;

import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.googlemeet.service.GoogleMeetService;
import com.haertz.be.payment.dto.*;
import com.haertz.be.payment.entity.Payment;
import com.haertz.be.payment.entity.PaymentMethod;
import com.haertz.be.payment.entity.PaymentStatus;
import com.haertz.be.payment.exception.PaymentErrorCode;
import com.haertz.be.payment.repository.temp;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log
public class BankTransferService {
    private final PaymentSaveService paymentSaveService;
    private final GoogleMeetService googleMeetService;
    private final temp temp;

    public BankTransferDto banktransferrequest(BankTransferRequestDto requestDTO) {
        // 계좌이체 요청 정보가 유효한지 확인(나중에 예약관련된 검증도 추가)
        if (requestDTO == null  || requestDTO.getPartner_order_id() == null) {
            throw new BaseException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
        }
        try {
            PaymentSaveDto paymentSaveDto = new PaymentSaveDto();
            paymentSaveDto.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
            paymentSaveDto.setPaymentDate(new Date());
            paymentSaveDto.setPaymentStatus(PaymentStatus.PENDING);
            paymentSaveDto.setUserId(Long.valueOf(requestDTO.getPartner_user_id()));
            paymentSaveDto.setTotalAmount(new BigDecimal(requestDTO.getTotal_amount()));
            log.info(paymentSaveDto.toString());
            Payment savedpayment=paymentSaveService.savePayment(paymentSaveDto);
            /*
            //구글 미팅 링크 생성
            GoogleMeetRequestDto googleMeetRequestDto = new GoogleMeetRequestDto();
            googleMeetRequestDto.setReservationId(1L); //현재는 테스트용. 예약엔티티 설정 후 변경.
            googleMeetRequestDto.setUserId(Long.valueOf(requestDTO.getPartner_user_id()));
            googleMeetRequestDto.setGooglemeetaccessToken("ya29.a0AXeO80RuWDPXpOuB--diMWqZ0g2MCyjZol0GiWk9643x9wGR8dwTwfeIJWSefvgdpAR9U8P9jJbCM0VBO-gC4rtstWB3UFUMsDV8AE6okedSaoVNu0BNO8NpxfflqNB6oh3oJW4kHvuJnHPCPly0mZWrY-QU_QhUcLgWd12SrQaCgYKAVoSARISFQHGX2MiLTsc60kevgi5VbtqxCGF_Q0177");
            GoogleMeetDto googleMeetingLink= googleMeetService.googlemeetrequest(googleMeetRequestDto);
            log.info(googleMeetingLink.toString());

             */

            // 계좌이체 후 DTO 반환
            BankTransferDto bankTransferDto = new BankTransferDto();
            bankTransferDto.setPaymentId(savedpayment.getPaymentId());
            bankTransferDto.setGoogleMeetingLink("구글미팅 링크 생성로직은 아직 구현 전..");
            //String googlemeetlink=googleMeetingLink.getGoogleMeetingLink();
            //bankTransferDto.setGoogleMeetingLink(googlemeetlink);
            bankTransferDto.setCreated_at(new Date());
            bankTransferDto.setPaymentstatus(savedpayment.getPaymentStatus());
            return bankTransferDto;
        } catch (Exception ex) {
            // 결제 처리 중 오류 발생 시
            throw new BaseException(PaymentErrorCode.PAYMENT_PROCESSING_ERROR);
        }
    }
    public BankTransferCancelDto banktransfercancel(BankTransferCancelRequestDto requestDTO) {
        // 요청 파라미터 검증
        if (requestDTO == null || requestDTO.getPaymentId() == null) {
            throw new BaseException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
        }
        try{
            Payment payment= temp.findByPaymentId(requestDTO.getPaymentId())
                    .orElseThrow(() -> new BaseException(PaymentErrorCode.PAYMENT_NOT_FOUND));
            //결제 status 업데이트
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            temp.save(payment);

            //디자이너 예약 확정 엔티티에서 관련 데이터 삭제(데이터들로 조회후 삭제 처리 구현)

            //취소 완료 응답 dto 생성
            BankTransferCancelDto bankTransferCancelDto = new BankTransferCancelDto();
            bankTransferCancelDto.setPaymentId(payment.getPaymentId());
            bankTransferCancelDto.setPaymentstatus(payment.getPaymentStatus());
            return bankTransferCancelDto;
        } catch (Exception ex) {
            throw new BaseException(PaymentErrorCode.PAYMENT_CANCELLATION_ERROR);
        }
    }
}
