package com.haertz.be.payment.Service;

import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.googlemeet.dto.GoogleMeetDto;
import com.haertz.be.googlemeet.dto.GoogleMeetRequestDto;
import com.haertz.be.googlemeet.service.GoogleMeetService;
import com.haertz.be.payment.dto.BankTransferDto;
import com.haertz.be.payment.dto.BankTransferRequestDto;
import com.haertz.be.payment.dto.PaymentSaveDto;
import com.haertz.be.payment.entity.PaymentMethod;
import com.haertz.be.payment.entity.PaymentStatus;
import com.haertz.be.payment.exception.PaymentErrorCode;
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

    public BankTransferDto banktransferrequest(BankTransferRequestDto requestDTO) {
        // 계좌이체 요청 정보가 유효한지 확인
        if (requestDTO == null || requestDTO.getTid() == null || requestDTO.getPartner_order_id() == null) {
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
            paymentSaveService.savePayment(paymentSaveDto);
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
            bankTransferDto.setGoogleMeetingLink("구글미팅 링크 생성로직은 아직 구현 전..");
            //String googlemeetlink=googleMeetingLink.getGoogleMeetingLink();
            //bankTransferDto.setGoogleMeetingLink(googlemeetlink);
            bankTransferDto.setCreated_at(new Date());
            return bankTransferDto;
        } catch (Exception ex) {
            // 결제 처리 중 오류 발생 시
            throw new BaseException(PaymentErrorCode.PAYMENT_PROCESSING_ERROR);
        }
    }
}
