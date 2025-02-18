package com.haertz.be.payment.service;

import com.haertz.be.booking.dto.request.PreBookingRequest;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.booking.service.DesignerScheduleDomainService;
import com.haertz.be.booking.repository.DesignerScheduleRepository;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import com.haertz.be.googlemeet.dto.GoogleMeetDto;
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
    private final AuthenticatedUserUtils userUtils;
    private final DesignerScheduleDomainService designerScheduleDomainService;
    private final DesignerScheduleRepository designerScheduleRepository;


    public BankTransferDto banktransferrequest(BankTransferRequestDto requestDTO) {
        Long currentUserId = userUtils.getCurrentUserId();

        // 계좌이체 요청 정보가 유효한지 확인(나중에 예약관련된 검증도 추가)
        if (requestDTO == null) {
            throw new BaseException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
        }
        try {
            //디자이너스케쥴(임시예약정보 생성해 다른 사용자가 예약하지 못하도록
            PreBookingRequest preBooking = new PreBookingRequest(
                    requestDTO.getDesignerId(),       // 디자이너 ID
                    requestDTO.getBookingDate(),      // 예약 날짜
                    requestDTO.getBookingTime()       // 예약 시간
            );
            // 임시 스케줄 생성 후 해당 스케줄의 ID를 반환받음.
            Long tempScheduleId = designerScheduleDomainService.registerTempSchedule(currentUserId, preBooking);

            PaymentSaveDto paymentSaveDto = new PaymentSaveDto();
            paymentSaveDto.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
            paymentSaveDto.setPaymentDate(new Date());
            paymentSaveDto.setPaymentStatus(PaymentStatus.PENDING);
            paymentSaveDto.setUserId(currentUserId);
            paymentSaveDto.setTotalAmount(new BigDecimal(requestDTO.getTotal_amount()));
            paymentSaveDto.setPartnerOrderId(String.valueOf(tempScheduleId));
            log.info(paymentSaveDto.toString());
            Payment savedpayment=paymentSaveService.savePayment(paymentSaveDto);

            // 디자이너 스케줄을 조회해서, 해당 스케줄의 디자이너 ID와 예약 시간으로 구글 미팅 링크 조회
            DesignerSchedule schedule = designerScheduleRepository.findById(tempScheduleId)
                    .orElseThrow(() -> new BaseException(BookingErrorCode.DESIGNER_SCHEDULE_NOT_FOUND));

            GoogleMeetDto meetDto = googleMeetService.getMeetingLink(schedule.getDesignerId(), schedule.getBookingTime());
            String googleMeetlink=meetDto.getGoogleMeetingLink();

            // 계좌이체 후 DTO 반환
            BankTransferDto bankTransferDto = new BankTransferDto();
            bankTransferDto.setPaymentId(savedpayment.getPaymentId());
            bankTransferDto.setDesignerScheduleId(String.valueOf(tempScheduleId));
            bankTransferDto.setGoogleMeetingLink(googleMeetlink);
            bankTransferDto.setCreated_at(new Date());
            bankTransferDto.setPaymentstatus(savedpayment.getPaymentStatus());
            designerScheduleDomainService.confirmScheduleAfterPayment(tempScheduleId, PaymentStatus.PENDING);

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

            //디자이너스케줄 확정 엔티티에서 관련 데이터 삭제(데이터들로 조회후 삭제 처리 구현)
            Long designerScheduleId= Long.valueOf(payment.getPartnerOrderId());
            designerScheduleDomainService.deleteScheduleAfterFailedPayment(designerScheduleId);

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
