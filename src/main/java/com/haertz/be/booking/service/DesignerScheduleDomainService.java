package com.haertz.be.booking.service;

import com.haertz.be.booking.adaptor.DesignerScheduleAdaptor;
import com.haertz.be.booking.converter.BookingConverter;
import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.dto.request.PreBookingRequest;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.payment.entity.PaymentStatus;
import com.haertz.be.payment.exception.PaymentErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DesignerScheduleDomainService {
    private final DesignerScheduleAdaptor designerScheduleAdaptor;
    @Transactional
    public Long registerTempSchedule(Long userId, PreBookingRequest preBooking) {
        if (designerScheduleAdaptor.hasBookingByTimeSlot(preBooking.designerId(), preBooking.bookingDate(), preBooking.bookingTime())) {
            throw new BaseException(BookingErrorCode.BOOKING_ALREADY_REGISTERED);
        }

        validateBookingDateTime(preBooking);
        DesignerSchedule designerSchedule = BookingConverter.toDesignerSchedule(userId, preBooking, PaymentStatus.IN_PROGRESS);
        designerScheduleAdaptor.save(designerSchedule);

        return designerSchedule.getDesignerScheduleId();
    }
    @Transactional
    public void confirmScheduleAfterPayment(Long designerScheduleId, PaymentStatus paymentStatus) {
        DesignerSchedule designerSchedule = designerScheduleAdaptor.findById(designerScheduleId);

        if (paymentStatus != PaymentStatus.PENDING && paymentStatus != PaymentStatus.COMPLETED) {
            throw new BaseException(PaymentErrorCode.PAYMENT_STATUS_INVALID);
        }

        if (designerSchedule.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new BaseException(PaymentErrorCode.PAYMENT_ALREADY_COMPLETED);
        }

        designerSchedule.markAsCompleted(paymentStatus);
        designerScheduleAdaptor.save(designerSchedule);
    }

    @Transactional
    public void deleteScheduleAfterFailedPayment(Long designerScheduleId) {
        designerScheduleAdaptor.deleteById(designerScheduleId);
    }

    private void validateBookingDateTime(PreBookingRequest preBookingRequest){
        LocalDate bookingDate = preBookingRequest.bookingDate();
        LocalTime bookingTime = preBookingRequest.bookingTime();
        LocalTime now = LocalTime.now().withNano(0);
        LocalTime minTime = LocalTime.of(10, 0);
        LocalTime maxTime = LocalTime.of(19, 30);

        if (bookingDate.isBefore(LocalDate.now())) {
            throw new BaseException(BookingErrorCode.INVALID_BOOKING_DATE);
        }

        if (bookingDate.isEqual(LocalDate.now()) && bookingTime.isBefore(now)) {
            throw new BaseException(BookingErrorCode.INVALID_BOOKING_TIME);
        }

        if (bookingTime.getMinute() % 30 != 0) {
            throw new BaseException(BookingErrorCode.INVALID_TIME_FORMAT);
        }

        if (bookingTime.isBefore(minTime) || bookingTime.isAfter(maxTime)) {
            throw new BaseException(BookingErrorCode.OUT_OF_AVAILABLE_TIME_RANGE);
        }

    }



}
