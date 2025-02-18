package com.haertz.be.booking.usecase;

import com.haertz.be.booking.adaptor.BookingAdaptor;
import com.haertz.be.booking.entity.Booking;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.booking.repository.BookingRepository;
import com.haertz.be.booking.service.BookingDomainService;
import com.haertz.be.booking.service.DesignerScheduleDomainService;
import com.haertz.be.common.annotation.UseCase;
import com.haertz.be.common.exception.GlobalErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.common.utils.AuthenticatedUserUtils;
import com.haertz.be.payment.dto.BankTransferCancelRequestDto;
import com.haertz.be.payment.dto.KakaoPayCancelRequestDto;
import com.haertz.be.payment.entity.Payment;
import com.haertz.be.payment.entity.PaymentMethod;
import com.haertz.be.payment.exception.PaymentErrorCode;
import com.haertz.be.payment.repository.temp;
import com.haertz.be.payment.service.BankTransferService;
import com.haertz.be.payment.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@UseCase
@RequiredArgsConstructor
public class CancelBookingWithRefundUseCase {

    private final BookingDomainService bookingDomainService;
    private final DesignerScheduleDomainService designerScheduleDomainService;
    private final BankTransferService bankTransferService;
    private final KakaoPayService kakaoPayService;
    private final BookingAdaptor bookingAdaptor;
    private final AuthenticatedUserUtils userUtils;
    private final temp temp;

    @Transactional
    public void execute(Long bookingId){
        Booking booking = bookingAdaptor.findById(bookingId);

        if(!booking.getUserId().equals(userUtils.getCurrentUserId())){
            throw new BaseException(GlobalErrorCode.UNAUTHORIZED_REQUEST_ERROR);
        }
        // 예약에서 디자이너 스케줄 ID 추출
        Long designerScheduleId = booking.getDesignerScheduleId();
        if (designerScheduleId == null) {
            throw new BaseException(BookingErrorCode.DESIGNER_SCHEDULE_NOT_FOUND);
        }
        // Payment 조회
        Payment payment = temp.findByPartnerOrderId(String.valueOf(designerScheduleId))
                .orElseThrow(() -> new BaseException(PaymentErrorCode.PAYMENT_NOT_FOUND));
        if (payment.getPaymentMethod() == PaymentMethod.KAKAO_PAY) {
            KakaoPayCancelRequestDto kakaoCancelrequestDto = new KakaoPayCancelRequestDto();
            kakaoCancelrequestDto.setTid(payment.getPaymentTransaction());
            // partner_order_id로 스케줄 ID 사용
            kakaoCancelrequestDto.setPartnerOrderId(String.valueOf(designerScheduleId));
            kakaoCancelrequestDto.setCancelAmount(payment.getTotalAmount().intValue());
            kakaoCancelrequestDto.setCancelTaxFreeAmount(0);
            // 취소 API 호출
            kakaoPayService.kakaoPayCancel(kakaoCancelrequestDto);
        } else if (payment.getPaymentMethod() == PaymentMethod.BANK_TRANSFER) {
            // Bank Transfer 결제 취소
            BankTransferCancelRequestDto bankCancelrequestDto = new BankTransferCancelRequestDto();
            bankCancelrequestDto.setPaymentId(payment.getPaymentId());
            bankTransferService.banktransfercancel(bankCancelrequestDto);
        } else {
            throw new BaseException(PaymentErrorCode.INVALID_PAYMENT_REQUEST);
        }
        designerScheduleDomainService.deleteScheduleAfterFailedPayment(booking.getDesignerScheduleId());
        bookingDomainService.cancelBooking(booking);
    }
}
