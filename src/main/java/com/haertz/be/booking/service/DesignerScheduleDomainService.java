package com.haertz.be.booking.service;

import com.haertz.be.booking.adaptor.DesignerScheduleAdaptor;
import com.haertz.be.booking.converter.BookingConverter;
import com.haertz.be.booking.dto.request.BookingInfoRequest;
import com.haertz.be.booking.entity.DesignerSchedule;
import com.haertz.be.booking.exception.BookingErrorCode;
import com.haertz.be.common.exception.base.BaseException;
import com.haertz.be.payment.entity.PaymentStatus;
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
    public void registerSchedule(BookingInfoRequest bookingInfo, Long userId, PaymentStatus paymentStatus){
        validateBookingDateTime(bookingInfo);
        DesignerSchedule designerSchedule = BookingConverter.toDesignerSchedule(bookingInfo, userId, paymentStatus);
        designerScheduleAdaptor.save(designerSchedule);
    }

    private void validateBookingDateTime(BookingInfoRequest bookingInfo){
        LocalDate bookingDate = bookingInfo.bookingDate();
        LocalTime bookingTime = bookingInfo.bookingTime();
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
